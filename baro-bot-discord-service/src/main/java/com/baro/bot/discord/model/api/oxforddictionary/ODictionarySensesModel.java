package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionarySensesModel {

    private String[] definitions;
    private ODictionaryExamplesModel[] examples;
    private String id;
    private ODictionaryRegistersModel[] registers;
    private ODictionarySensesModel[] subsenses;
    private ODictionaryRegistersModel[] domains;

    public ODictionarySensesModel() {
    }

    public ODictionarySensesModel(String[] definitions, ODictionaryExamplesModel[] examples, String id, ODictionaryRegistersModel[] registers, ODictionarySensesModel[] subsenses, ODictionaryRegistersModel[] domains) {
        this.definitions = definitions;
        this.examples = examples;
        this.id = id;
        this.registers = registers;
        this.subsenses = subsenses;
        this.domains = domains;
    }

    public String[] getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String[] definitions) {
        this.definitions = definitions;
    }

    public ODictionaryExamplesModel[] getExamples() {
        return examples;
    }

    public void setExamples(ODictionaryExamplesModel[] examples) {
        this.examples = examples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ODictionaryRegistersModel[] getRegisters() {
        return registers;
    }

    public void setRegisters(ODictionaryRegistersModel[] registers) {
        this.registers = registers;
    }

    public ODictionarySensesModel[] getSubsenses() {
        return subsenses;
    }

    public void setSubsenses(ODictionarySensesModel[] subsenses) {
        this.subsenses = subsenses;
    }

    public ODictionaryRegistersModel[] getDomains() {
        return domains;
    }

    public void setDomains(ODictionaryRegistersModel[] domains) {
        this.domains = domains;
    }
}
