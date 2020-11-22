package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryEntriesModel {

    private String[] etymologies;
    private String homographNumber;
    private ODictionaryPronunciationsModel[] pronunciations;
    private ODictionarySensesModel[] senses;

    public ODictionaryEntriesModel() {
    }

    public ODictionaryEntriesModel(String[] etymologies, String homographNumber, ODictionaryPronunciationsModel[] pronunciations, ODictionarySensesModel[] senses) {
        this.etymologies = etymologies;
        this.homographNumber = homographNumber;
        this.pronunciations = pronunciations;
        this.senses = senses;
    }

    public String[] getEtymologies() {
        return etymologies;
    }

    public void setEtymologies(String[] etymologies) {
        this.etymologies = etymologies;
    }

    public String getHomographNumber() {
        return homographNumber;
    }

    public void setHomographNumber(String homographNumber) {
        this.homographNumber = homographNumber;
    }

    public ODictionaryPronunciationsModel[] getPronunciations() {
        return pronunciations;
    }

    public void setPronunciations(ODictionaryPronunciationsModel[] pronunciations) {
        this.pronunciations = pronunciations;
    }

    public ODictionarySensesModel[] getSenses() {
        return senses;
    }

    public void setSenses(ODictionarySensesModel[] senses) {
        this.senses = senses;
    }
}
