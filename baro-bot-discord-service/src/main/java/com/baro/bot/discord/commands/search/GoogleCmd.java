package com.baro.bot.discord.commands.search;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.config.BotConfig;
import com.baro.bot.discord.model.api.google.GoogleSearchApiModel;
import org.jsoup.internal.StringUtil;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "google";
    public static final String GOOGLE_URL = "https://www.googleapis.com/customsearch/v1?safe=medium&cx=%s&key=%s&num=%d&q=%s";
    public static final int AMOUNT_OF_RESULTS = 3;
    private final BotConfig config;

    public GoogleCmd(BotConfig config) {
        this.config = config;
    }

    @Override
    public void execute(CommandContext ctx) {

        if (config.getGoogleApiKey() == null ||
                config.getGoogleApiKey().isBlank() ||
                config.getGoogleEngineId() == null ||
                config.getGoogleEngineId().isBlank()) {
            sendError(ctx, "Google Search credentials are missing. Please contact the owner to enable this command");
            return;
        }
        List<String> results = new ArrayList<>();
        WebClient
                .builder()
                .build()
                .get()
                .uri(URI.create(String.format(GOOGLE_URL, config.getGoogleEngineId(), config.getGoogleApiKey(),
                        AMOUNT_OF_RESULTS, URLEncoder.encode(ctx.getArgs(), StandardCharsets.UTF_8))))
                .retrieve()
                .bodyToMono(GoogleSearchApiModel.class)
                .flatMapMany(googleSearchApiModel -> Flux.just(googleSearchApiModel.getItems()))
                .doOnNext(googleSearchItemsModel -> results.add(googleSearchItemsModel.getLink()))
                .doFinally(signalType -> ctx.getEvent().getChannel().sendMessage(StringUtil.join(results, "\n")).queue())
                .doOnError(throwable -> sendError(ctx, throwable.getMessage()))
                .subscribe();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Google a word";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("g");
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
        return true;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 600;
    }
}
