package com.baro.bot.discord.model.api.google;

public class GoogleSearchContextEntryModel {

    private String title;

    public GoogleSearchContextEntryModel() {
    }

    public GoogleSearchContextEntryModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
