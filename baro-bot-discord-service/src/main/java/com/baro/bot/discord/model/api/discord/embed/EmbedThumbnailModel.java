package com.baro.bot.discord.model.api.discord.embed;

public class EmbedThumbnailModel {

    private String url;

    public EmbedThumbnailModel() {
    }

    public EmbedThumbnailModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
