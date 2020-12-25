package com.baro.bot.discord.model.api.discord.embed;

public class EmbedImageModel {

    private String url;

    public EmbedImageModel() {
    }

    public EmbedImageModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
