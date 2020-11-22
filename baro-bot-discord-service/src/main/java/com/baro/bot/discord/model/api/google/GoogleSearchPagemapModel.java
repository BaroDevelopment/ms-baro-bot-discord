package com.baro.bot.discord.model.api.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleSearchPagemapModel {

    private GoogleSearchOfferModel offer;
    @JsonProperty("cse_thumbnail")
    private GoogleSearchCseThumbnail thumbnail;
    private GoogleSearchMetaTagsModel metatags;
    @JsonProperty("cse_image")
    private GoogleSearchCseImage image;

    public GoogleSearchPagemapModel() {
    }

    public GoogleSearchPagemapModel(GoogleSearchOfferModel offer, GoogleSearchCseThumbnail thumbnail, GoogleSearchMetaTagsModel metatags, GoogleSearchCseImage image) {
        this.offer = offer;
        this.thumbnail = thumbnail;
        this.metatags = metatags;
        this.image = image;
    }

    public GoogleSearchOfferModel getOffer() {
        return offer;
    }

    public void setOffer(GoogleSearchOfferModel offer) {
        this.offer = offer;
    }

    public GoogleSearchCseThumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(GoogleSearchCseThumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public GoogleSearchMetaTagsModel getMetatags() {
        return metatags;
    }

    public void setMetatags(GoogleSearchMetaTagsModel metatags) {
        this.metatags = metatags;
    }

    public GoogleSearchCseImage getImage() {
        return image;
    }

    public void setImage(GoogleSearchCseImage image) {
        this.image = image;
    }
}
