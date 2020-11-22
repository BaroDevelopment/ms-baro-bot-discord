package com.baro.bot.discord.commands.search;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.model.api.urban.UrbanDictionaryEntryModel;
import com.baro.bot.discord.model.api.urban.UrbanDictionaryModel;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.ColorUtil;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UrbanCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "urban";
    public static final Logger LOGGER = LoggerFactory.getLogger(UrbanCmd.class);
    public static final String BASE_URL = "https://api.urbandictionary.com";

    public static final String THUMBS_UP = "\uD83D\uDC4D";
    public static final String THUMBS_DOWN = "\uD83D\uDC4E";

    private final Paginator.Builder pbuilder;

    public UrbanCmd(BaroBot bot) {
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
        pbuilder.clearItems();
        Mono<UrbanDictionaryModel> urbanMono = WebClient.builder()
                .baseUrl(BASE_URL)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v0/define")
                        .queryParam("term", ctx.getArgs()).build())
                .retrieve()
                .bodyToMono(UrbanDictionaryModel.class);

        urbanMono.flatMapMany(r -> Flux.just(r.getList()))
                .doOnNext(urbanDictionaryEntryModel -> pbuilder.addItems(getUrbanString(urbanDictionaryEntryModel)))
                .doOnComplete(() -> sendEmbed(ctx))
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        findAndSendAudio(urbanMono, ctx);
    }

    void findAndSendAudio(Mono<UrbanDictionaryModel> urbanMono, CommandContext ctx) {
        try {
            String firstUrl = urbanMono.flatMapMany(r -> Flux.just(r.getList()))
                    .filter(u -> u.getSound_urls() != null && u.getSound_urls().length > 0)
                    .blockFirst().getSound_urls()[0];
            try (InputStream input = new URL(firstUrl).openStream()) {
                ctx.getEvent().getChannel().sendFile(input, "audio.mp3").queue();
            }
        } catch (NullPointerException | IOException e) {

        }
    }

    void sendEmbed(CommandContext ctx) {
        int page = 1;
        Paginator p = pbuilder.setColor(new ColorUtil().getRandomColor())
                .setText("Urban Dictionary Results:")
                .setUsers(ctx.getEvent().getAuthor())
                .build();
        p.paginate(ctx.getEvent().getChannel(), page);
    }

    String getUrbanString(UrbanDictionaryEntryModel urban) {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + urban.getWord() + "]" + "(" + urban.getPermalink() + ")");
        sb.append("\n\n");
        sb.append("***Definition***\n");
        sb.append(urban.getDefinition().substring(0, Math.min(urban.getDefinition().length(), 1024)));

        if (urban.getExample() != null && !urban.getExample().isBlank()) {
            sb.append("\n\n");
            sb.append("***Eample***\n");
            sb.append(urban.getExample().substring(0, Math.min(urban.getExample().length(), 1024)));
        }

        sb.append("\n\n");
        sb.append(THUMBS_UP + urban.getThumbs_up().toString());
        sb.append("\t\t");
        sb.append(THUMBS_DOWN + urban.getThumbs_down().toString());

        if (urban.getSound_urls() != null && urban.getSound_urls().length > 0) {
            sb.append("\n\n");
            sb.append("***Sound File***");
            sb.append("\n");
            for (String s : urban.getSound_urls()) {
                sb.append("[Audio]" + "(" + s + ")\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Query the Urban Dictionary API";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SEARCH;
    }

    @Override
    public boolean getArgs() {
        return true;
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
    public List<String> getAliases() {
        return Collections.singletonList("urban-dictionary");
    }


    @Override
    public long getCooldown() {
        return 10;
    }
}
