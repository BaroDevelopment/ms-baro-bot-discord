package com.baro.bot.discord.model.api.discord.embed;

public class EmbedModel {
    private String title;
    private String description;
    private String url;
    private Integer color;
    private String timestamp;
    private EmbedFooterModel footer;
    private EmbedThumbnailModel thumbnail;
    private EmbedImageModel image;
    private EmbedAuthorModel author;
    private EmbedFieldModel[] fields;

    public EmbedModel() {
    }

    public EmbedModel(String title, String description, String url, Integer color, String timestamp, EmbedFooterModel footer, EmbedThumbnailModel thumbnail, EmbedImageModel image, EmbedAuthorModel author, EmbedFieldModel[] fields) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.color = color;
        this.timestamp = timestamp;
        this.footer = footer;
        this.thumbnail = thumbnail;
        this.image = image;
        this.author = author;
        this.fields = fields;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public EmbedFooterModel getFooter() {
        return footer;
    }

    public void setFooter(EmbedFooterModel footer) {
        this.footer = footer;
    }

    public EmbedThumbnailModel getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(EmbedThumbnailModel thumbnail) {
        this.thumbnail = thumbnail;
    }

    public EmbedImageModel getImage() {
        return image;
    }

    public void setImage(EmbedImageModel image) {
        this.image = image;
    }

    public EmbedAuthorModel getAuthor() {
        return author;
    }

    public void setAuthor(EmbedAuthorModel author) {
        this.author = author;
    }

    public EmbedFieldModel[] getFields() {
        return fields;
    }

    public void setFields(EmbedFieldModel[] fields) {
        this.fields = fields;
    }
}
