package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryExamplesModel {

    private String text;
    private ODictionaryRegistersModel[] registers;

    public ODictionaryExamplesModel() {
    }

    public ODictionaryExamplesModel(String text, ODictionaryRegistersModel[] registers) {
        this.text = text;
        this.registers = registers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ODictionaryRegistersModel[] getRegisters() {
        return registers;
    }

    public void setRegisters(ODictionaryRegistersModel[] registers) {
        this.registers = registers;
    }
}
