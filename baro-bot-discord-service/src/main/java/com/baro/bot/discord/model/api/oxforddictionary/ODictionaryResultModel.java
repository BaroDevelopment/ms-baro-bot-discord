package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryResultModel {

    private String id;
    private String language;
    private String type;
    private String word;
    private ODictionaryLecialEntriesModel[] lexicalEntries;

    public ODictionaryResultModel() {
    }

    public ODictionaryResultModel(String id, String language, String type, String word, ODictionaryLecialEntriesModel[] lexicalEntries) {
        this.id = id;
        this.language = language;
        this.type = type;
        this.word = word;
        this.lexicalEntries = lexicalEntries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ODictionaryLecialEntriesModel[] getLexicalEntries() {
        return lexicalEntries;
    }

    public void setLexicalEntries(ODictionaryLecialEntriesModel[] lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
    }
}
