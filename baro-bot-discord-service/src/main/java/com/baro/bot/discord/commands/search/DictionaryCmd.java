package com.baro.bot.discord.commands.search;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.config.BotConfig;
import com.baro.bot.discord.model.api.oxforddictionary.*;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.ColorUtil;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DictionaryCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "define";
    public static final String LANGUAGE = "en-gb";
    public static final String FIELDS = "definitions%2Cdomains%2Cetymologies%2Cexamples%2Cpronunciations%2Cregions%2Cregisters%2CvariantForms";
    public static final String URL = "https://od-api.oxforddictionaries.com/api/v2/entries/";
    public static final String HEADER_APP_KEY = "app_key";
    public static final String HEADER_APP_ID = "app_id";
    private final BotConfig config;
    private final Paginator.Builder pbuilder;

    public DictionaryCmd(BotConfig config, BaroBot bot) {
        this.config = config;
        this.pbuilder = new Paginator.Builder()
                .setColumns(1)
                .setItemsPerPage(1)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(false)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException | IllegalStateException e) {
                        m.delete().queue();
                    }
                })
                .setEventWaiter(bot.getEventWaiter())
                .setTimeout(1, TimeUnit.MINUTES);
    }

    @Override
    public void execute(CommandContext ctx) {
        Mono<ODictionaryApiModel> dictionaryMono = WebClient
                .builder()
                .build()
                .get()
                .uri(URI.create(getUri(ctx.getArgs())))
                .header("Accept", "application/json")
                .header(HEADER_APP_KEY, config.getOxfordDictionaryApiKey())
                .header(HEADER_APP_ID, config.getOxfordDictionaryAppId())
                .retrieve()
                .bodyToMono(ODictionaryApiModel.class);

        dictionaryMono.flatMapMany(oDictionaryApiModel -> Flux.just(oDictionaryApiModel.getResults()))
                .doOnNext(oDictionaryResultModel -> pbuilder.addItems(getEntry(oDictionaryResultModel)))
                .doOnComplete(() -> sendEmbed(ctx))
                .doOnError(throwable -> sendError(ctx, throwable.getMessage()))
                .subscribe();

        findAndSendAudio(ctx, dictionaryMono);
    }

    private void findAndSendAudio(CommandContext ctx, Mono<ODictionaryApiModel> api) {
        try {
            for (ODictionaryResultModel result : api.block().getResults()) {
                for (ODictionaryLecialEntriesModel lexicalEntry : result.getLexicalEntries()) {
                    for (ODictionaryEntriesModel entry : lexicalEntry.getEntries()) {
                        if (entry != null && entry.getPronunciations() != null && entry.getPronunciations().length > 0) {
                            String audioUrl = entry.getPronunciations()[0].getAudioFile();
                            try {
                                InputStream input = new URL(audioUrl).openStream();
                                ctx.getEvent().getChannel().sendFile(input, "audio.mp3").queue();
                                return;
                            } catch (Exception e) {
                                // not present
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            // api is null
        }
    }

    private String getEntry(ODictionaryResultModel result) {
        StringBuilder sb = new StringBuilder();
        for (ODictionaryLecialEntriesModel lexicalEntry : result.getLexicalEntries()) {
            for (ODictionaryEntriesModel entry : lexicalEntry.getEntries()) {
                if (entry != null && entry.getEtymologies() != null && entry.getEtymologies().length > 0) {
                    sb.append("**Etymology:**\n");
                    sb.append("`-`");
                    sb.append(StringUtils.join(entry.getEtymologies(), "`-`"));
                    sb.append("\n\n");
                }
                if (entry != null && entry.getSenses() != null) {
                    for (ODictionarySensesModel sens : entry.getSenses()) {
                        if (sens.getDefinitions() != null && sens.getDefinitions().length > 0) {
                            sb.append("**Definition:**\n");
                            sb.append("`-`");
                            sb.append(StringUtils.join(sens.getDefinitions(), "`-`"));
                            sb.append("\n");
                        }
                        if (sens.getExamples() != null && sens.getExamples().length > 0) {
                            sb.append("**Example:**\n");
                            sb.append("`-`");
                            String[] examples = Arrays.stream(sens.getExamples())
                                    .map(ODictionaryExamplesModel::getText)
                                    .filter(s -> s != null && !s.isBlank())
                                    .toArray(String[]::new);
                            sb.append(StringUtils.join(examples, "`-`"));
                            sb.append("\n\n");
                        }
                    }
                }
            }
        }

        return sb.toString();
    }

    void sendEmbed(CommandContext ctx) {
        int page = 1;
        Paginator p = pbuilder.setColor(new ColorUtil().getRandomColor())
                .setText("Oxford Dictionary Results:")
                .setUsers(ctx.getEvent().getAuthor())
                .build();
        p.paginate(ctx.getEvent().getChannel(), page);
    }

    private String getUri(String query) {
        String queryEncoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return URL + LANGUAGE + "/" + queryEncoded + "?" + "fields=" + FIELDS + "&strictMatch=false";
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SEARCH;
    }

    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Override
    public EmbedBuilder getUsage() {
        return null;
    }

    @Override
    public List<String> getExamples() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("dic", "dictionary");
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return null;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return null;
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getDocumentationUrl() {
        return null;
    }
}
