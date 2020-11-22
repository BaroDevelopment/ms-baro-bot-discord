package com.baro.bot.discord.model.api.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleSearchMetaTagsModel {

    @JsonProperty("og:image")
    private String ogImage;
    @JsonProperty("og:type")
    private String ogType;
    @JsonProperty("twitter:card")
    private String twitterCard;
    @JsonProperty("og:site_name")
    private String ogSiteName;
    @JsonProperty("twitter:site:id")
    private String twitterSiteId;
    @JsonProperty("msvalidate.01")
    private String msvalidate;
    @JsonProperty("og:title")
    private String ogTitle;
    @JsonProperty("og:keywords")
    private String ogKeywords;
    @JsonProperty("twitter:creator")
    private String twitterCreator;
    @JsonProperty("og:description")
    private String ogDescription;
    @JsonProperty("twitter:image")
    private String twitterImage;
    @JsonProperty("twitter:site")
    private String twitterSite;
    @JsonProperty("twitter:creator:id")
    private String twitterCreatorId;
    private String viewport;
    @JsonProperty("og:locale")
    private String ogLocale;
    @JsonProperty("fb:admins")
    private String fbAdmins;
    @JsonProperty("og:url")
    private String ogUrl;

    public GoogleSearchMetaTagsModel() {
    }

    public GoogleSearchMetaTagsModel(String ogImage, String ogType, String twitterCard, String ogSiteName,
                                     String twitterSiteId, String msvalidate, String ogTitle, String ogKeywords,
                                     String twitterCreator, String ogDescription, String twitterImage, String twitterSite,
                                     String twitterCreatorId, String viewport, String ogLocale, String fbAdmins,
                                     String ogUrl) {
        this.ogImage = ogImage;
        this.ogType = ogType;
        this.twitterCard = twitterCard;
        this.ogSiteName = ogSiteName;
        this.twitterSiteId = twitterSiteId;
        this.msvalidate = msvalidate;
        this.ogTitle = ogTitle;
        this.ogKeywords = ogKeywords;
        this.twitterCreator = twitterCreator;
        this.ogDescription = ogDescription;
        this.twitterImage = twitterImage;
        this.twitterSite = twitterSite;
        this.twitterCreatorId = twitterCreatorId;
        this.viewport = viewport;
        this.ogLocale = ogLocale;
        this.fbAdmins = fbAdmins;
        this.ogUrl = ogUrl;
    }

    public String getOgImage() {
        return ogImage;
    }

    public void setOgImage(String ogImage) {
        this.ogImage = ogImage;
    }

    public String getOgType() {
        return ogType;
    }

    public void setOgType(String ogType) {
        this.ogType = ogType;
    }

    public String getTwitterCard() {
        return twitterCard;
    }

    public void setTwitterCard(String twitterCard) {
        this.twitterCard = twitterCard;
    }

    public String getOgSiteName() {
        return ogSiteName;
    }

    public void setOgSiteName(String ogSiteName) {
        this.ogSiteName = ogSiteName;
    }

    public String getTwitterSiteId() {
        return twitterSiteId;
    }

    public void setTwitterSiteId(String twitterSiteId) {
        this.twitterSiteId = twitterSiteId;
    }

    public String getMsvalidate() {
        return msvalidate;
    }

    public void setMsvalidate(String msvalidate) {
        this.msvalidate = msvalidate;
    }

    public String getOgTitle() {
        return ogTitle;
    }

    public void setOgTitle(String ogTitle) {
        this.ogTitle = ogTitle;
    }

    public String getOgKeywords() {
        return ogKeywords;
    }

    public void setOgKeywords(String ogKeywords) {
        this.ogKeywords = ogKeywords;
    }

    public String getTwitterCreator() {
        return twitterCreator;
    }

    public void setTwitterCreator(String twitterCreator) {
        this.twitterCreator = twitterCreator;
    }

    public String getOgDescription() {
        return ogDescription;
    }

    public void setOgDescription(String ogDescription) {
        this.ogDescription = ogDescription;
    }

    public String getTwitterImage() {
        return twitterImage;
    }

    public void setTwitterImage(String twitterImage) {
        this.twitterImage = twitterImage;
    }

    public String getTwitterSite() {
        return twitterSite;
    }

    public void setTwitterSite(String twitterSite) {
        this.twitterSite = twitterSite;
    }

    public String getTwitterCreatorId() {
        return twitterCreatorId;
    }

    public void setTwitterCreatorId(String twitterCreatorId) {
        this.twitterCreatorId = twitterCreatorId;
    }

    public String getViewport() {
        return viewport;
    }

    public void setViewport(String viewport) {
        this.viewport = viewport;
    }

    public String getOgLocale() {
        return ogLocale;
    }

    public void setOgLocale(String ogLocale) {
        this.ogLocale = ogLocale;
    }

    public String getFbAdmins() {
        return fbAdmins;
    }

    public void setFbAdmins(String fbAdmins) {
        this.fbAdmins = fbAdmins;
    }

    public String getOgUrl() {
        return ogUrl;
    }

    public void setOgUrl(String ogUrl) {
        this.ogUrl = ogUrl;
    }
}
