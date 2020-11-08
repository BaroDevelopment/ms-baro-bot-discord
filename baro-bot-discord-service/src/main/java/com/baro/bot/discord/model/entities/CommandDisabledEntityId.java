package com.baro.bot.discord.model.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommandDisabledEntityId implements Serializable {

    private String id;
    private String name;
    private String type;
    private String serverId;


    public CommandDisabledEntityId() {
    }

    public CommandDisabledEntityId(String id, String name, String type, String serverId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.serverId = serverId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
