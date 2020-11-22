package com.baro.bot.discord.model.api.urban;

import java.io.Serializable;

public class UrbanDictionaryModel implements Serializable {

    private UrbanDictionaryEntryModel[] list;

    public UrbanDictionaryModel() {
    }

    public UrbanDictionaryModel(UrbanDictionaryEntryModel[] list) {
        this.list = list;
    }

    public UrbanDictionaryEntryModel[] getList() {
        return list;
    }

    public void setList(UrbanDictionaryEntryModel[] list) {
        this.list = list;
    }
}
