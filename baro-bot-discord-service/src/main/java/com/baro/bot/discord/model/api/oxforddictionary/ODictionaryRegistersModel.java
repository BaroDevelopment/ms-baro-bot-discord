package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryRegistersModel {

    private String id;
    private String text;

    public ODictionaryRegistersModel() {
    }

    public ODictionaryRegistersModel(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
