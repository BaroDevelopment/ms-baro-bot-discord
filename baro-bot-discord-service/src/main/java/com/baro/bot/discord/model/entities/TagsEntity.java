package com.baro.bot.discord.model.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "tags")
public class TagsEntity {

    @EmbeddedId
    TagsEntityId tagsEntityId;
    @Column(length = 2056)
    private String value;

    public TagsEntity() {
    }

    public TagsEntity(TagsEntityId tagsEntityId, String value) {
        this.tagsEntityId = tagsEntityId;
        this.value = value;
    }

    public TagsEntityId getTagsEntityId() {
        return tagsEntityId;
    }

    public void setTagsEntityId(TagsEntityId tagsEntityId) {
        this.tagsEntityId = tagsEntityId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
