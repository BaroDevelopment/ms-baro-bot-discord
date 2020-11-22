package com.baro.bot.discord.model.api.google;

public class GoogleSearchUrlModel {
    private String type;
    private String template;

    public GoogleSearchUrlModel() {
    }

    public GoogleSearchUrlModel(String type, String template) {
        this.type = type;
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
