package com.baro.bot.discord.model.api.google;

public class GoogleSearchOfferModel {

    private String price;
    private String url;

    public GoogleSearchOfferModel() {
    }

    public GoogleSearchOfferModel(String price, String url) {
        this.price = price;
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
