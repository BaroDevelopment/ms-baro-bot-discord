package com.baro.bot.discord.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "guild")
public class GuildEntity {

    @Id
    @Column(unique = true, nullable = false)
    private Long serverId;
    @Column(nullable = false)
    private String prefix;
    @Column(nullable = false)
    private String serverName;
    @Column(nullable = false)
    private boolean welcomeMessage;
    @Column(nullable = false)
    private String welcomeAvatar;
    @Column(nullable = false)
    private String ticketMessage;
    @Column(nullable = false)
    private String welcomeDm;
    @Column(nullable = false)
    private String serverAvatar;

    public GuildEntity() {
    }

    public GuildEntity(Long serverId, String prefix, String serverName, boolean welcomeMessage, String welcomeAvatar, String ticketMessage, String welcomeDm, String serverAvatar) {
        this.serverId = serverId;
        this.prefix = prefix;
        this.serverName = serverName;
        this.welcomeMessage = welcomeMessage;
        this.welcomeAvatar = welcomeAvatar;
        this.ticketMessage = ticketMessage;
        this.welcomeDm = welcomeDm;
        this.serverAvatar = serverAvatar;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public boolean isWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(boolean welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getWelcomeAvatar() {
        return welcomeAvatar;
    }

    public void setWelcomeAvatar(String welcomeAvatar) {
        this.welcomeAvatar = welcomeAvatar;
    }

    public String getTicketMessage() {
        return ticketMessage;
    }

    public void setTicketMessage(String ticketMessage) {
        this.ticketMessage = ticketMessage;
    }

    public String getWelcomeDm() {
        return welcomeDm;
    }

    public void setWelcomeDm(String welcomeDm) {
        this.welcomeDm = welcomeDm;
    }

    public String getServerAvatar() {
        return serverAvatar;
    }

    public void setServerAvatar(String serverAvatar) {
        this.serverAvatar = serverAvatar;
    }
}
