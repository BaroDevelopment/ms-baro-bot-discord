package com.baro.bot.discord.model.redis;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.OffsetDateTime;

@EqualsAndHashCode
public class MessageModel implements Serializable {

    private String messageId;
    private String userId;
    private String guildId;
    private String channelId;
    private String content;
    private String attachment;
    private String creationTime;

    public MessageModel() {
    }

    public MessageModel(String messageId, String userId, String guildId, String channelId, String content, String attachment, String creationTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.guildId = guildId;
        this.channelId = channelId;
        this.content = content;
        this.attachment = attachment;
        this.creationTime = creationTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
