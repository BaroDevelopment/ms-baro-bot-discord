package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryPronunciationsModel {
    private String audioFile;
    private String[] dialects;
    private String phoneticNotation;
    private String phoneticSpelling;

    public ODictionaryPronunciationsModel() {
    }

    public ODictionaryPronunciationsModel(String audioFile, String[] dialects, String phoneticNotation, String phoneticSpelling) {
        this.audioFile = audioFile;
        this.dialects = dialects;
        this.phoneticNotation = phoneticNotation;
        this.phoneticSpelling = phoneticSpelling;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String[] getDialects() {
        return dialects;
    }

    public void setDialects(String[] dialects) {
        this.dialects = dialects;
    }

    public String getPhoneticNotation() {
        return phoneticNotation;
    }

    public void setPhoneticNotation(String phoneticNotation) {
        this.phoneticNotation = phoneticNotation;
    }

    public String getPhoneticSpelling() {
        return phoneticSpelling;
    }

    public void setPhoneticSpelling(String phoneticSpelling) {
        this.phoneticSpelling = phoneticSpelling;
    }
}
