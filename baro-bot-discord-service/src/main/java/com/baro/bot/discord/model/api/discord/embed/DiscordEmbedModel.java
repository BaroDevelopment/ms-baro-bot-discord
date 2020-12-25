package com.baro.bot.discord.model.api.discord.embed;

public class DiscordEmbedModel {

    private String content;
    private EmbedModel embed;

    public DiscordEmbedModel() {
    }

    public DiscordEmbedModel(String content, EmbedModel embed) {
        this.content = content;
        this.embed = embed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmbedModel getEmbed() {
        return embed;
    }

    public void setEmbed(EmbedModel embed) {
        this.embed = embed;
    }
}
