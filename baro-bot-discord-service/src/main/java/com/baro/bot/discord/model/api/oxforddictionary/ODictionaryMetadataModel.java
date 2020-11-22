package com.baro.bot.discord.model.api.oxforddictionary;

public class ODictionaryMetadataModel {
    private String operation;
    private String provider;
    private String schema;

    public ODictionaryMetadataModel() {
    }

    public ODictionaryMetadataModel(String operation, String provider, String schema) {
        this.operation = operation;
        this.provider = provider;
        this.schema = schema;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
