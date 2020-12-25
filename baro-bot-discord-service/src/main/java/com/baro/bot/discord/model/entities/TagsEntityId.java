package com.baro.bot.discord.model.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TagsEntityId implements Serializable {

    private Long serverId;
    private String tag;

    public TagsEntityId() {
    }

    public TagsEntityId(Long serverId, String tag) {
        this.serverId = serverId;
        this.tag = tag;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
