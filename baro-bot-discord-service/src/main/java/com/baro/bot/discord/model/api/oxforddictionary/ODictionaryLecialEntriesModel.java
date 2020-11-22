package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryLecialEntriesModel {

    private ODictionaryEntriesModel[] entries;
    private String language;
    private ODictionaryRegistersModel lexicalCategory;
    private String text;

    public ODictionaryLecialEntriesModel() {
    }

    public ODictionaryLecialEntriesModel(ODictionaryEntriesModel[] entries, String language, ODictionaryRegistersModel lexicalCategory, String text) {
        this.entries = entries;
        this.language = language;
        this.lexicalCategory = lexicalCategory;
        this.text = text;
    }

    public ODictionaryEntriesModel[] getEntries() {
        return entries;
    }

    public void setEntries(ODictionaryEntriesModel[] entries) {
        this.entries = entries;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ODictionaryRegistersModel getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(ODictionaryRegistersModel lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
