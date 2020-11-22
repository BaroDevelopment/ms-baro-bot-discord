package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryApiModel {
    private String id;
    private ODictionaryMetadataModel metadata;
    private String word;
    private ODictionaryResultModel[] results;

    public ODictionaryApiModel() {
    }

    public ODictionaryApiModel(String id, ODictionaryMetadataModel metadata, String word, ODictionaryResultModel[] results) {
        this.id = id;
        this.metadata = metadata;
        this.word = word;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ODictionaryMetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(ODictionaryMetadataModel metadata) {
        this.metadata = metadata;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ODictionaryResultModel[] getResults() {
        return results;
    }

    public void setResults(ODictionaryResultModel[] results) {
        this.results = results;
    }
}
