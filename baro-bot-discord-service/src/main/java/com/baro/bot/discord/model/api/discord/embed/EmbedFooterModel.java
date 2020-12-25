package com.baro.bot.discord.model.api.discord.embed;

public class EmbedFooterModel {

    private String icon_url;
    private String text;

    public EmbedFooterModel() {
    }

    public EmbedFooterModel(String icon_url, String text) {
        this.icon_url = icon_url;
        this.text = text;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
