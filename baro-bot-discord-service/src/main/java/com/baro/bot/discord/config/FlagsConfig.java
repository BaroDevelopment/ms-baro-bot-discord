package com.baro.bot.discord.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:channelflags.properties")
public class FlagsConfig {

    private final String ticket;
    private final String vote;
    private final String lock;
    private final String nameLog;
    private final String discriminatorLog;
    private final String avatarLog;
    private final String statusLog;
    private final String typingLog;
    private final String activityLog;
    private final String messageDeleteLog;
    private final String messageEditLog;
    private final String textChannelUpdateLog;
    private final String voiceChannelUpdateLog;
    private final String textChannelTopicUpdateLog;
    private final String welcomeLog;
    private final String voiceChannelJoinLog;
    private final String voiceChannelLeaveLog;

    public FlagsConfig
            (
                    @Value("${TICKET}") String ticket,
                    @Value("${VOTE}") String vote,
                    @Value("${LOCK}") String lock,
                    @Value("${nameLog}") String nameLog,
                    @Value("${discriminatorLog}") String discriminatorLog,
                    @Value("${avatarLog}") String avatarLog,
                    @Value("${statusLog}") String statusLog,
                    @Value("${typingLog}") String typingLog,
                    @Value("${activityLog}") String activityLog,
                    @Value("${messageDeleteLog}") String messageDeleteLog,
                    @Value("${messageEditLog}") String messageEditLog,
                    @Value("${textChannelUpdateLog}") String textChannelUpdateLog,
                    @Value("${voiceChannelUpdateLog}") String voiceChannelUpdateLog,
                    @Value("${textChannelTopicUpdateLog}") String textChannelTopicUpdateLog,
                    @Value("${welcomeLog}") String welcomeLog,
                    @Value("${voiceChannelJoinLog}") String voiceChannelJoinLog,
                    @Value("${voiceChannelLeaveLog}") String voiceChannelLeaveLog
            ) {
        this.ticket = ticket;
        this.vote = vote;
        this.lock = lock;
        this.nameLog = nameLog;
        this.discriminatorLog = discriminatorLog;
        this.avatarLog = avatarLog;
        this.statusLog = statusLog;
        this.typingLog = typingLog;
        this.activityLog = activityLog;
        this.messageDeleteLog = messageDeleteLog;
        this.messageEditLog = messageEditLog;
        this.textChannelUpdateLog = textChannelUpdateLog;
        this.voiceChannelUpdateLog = voiceChannelUpdateLog;
        this.textChannelTopicUpdateLog = textChannelTopicUpdateLog;
        this.welcomeLog = welcomeLog;
        this.voiceChannelJoinLog = voiceChannelJoinLog;
        this.voiceChannelLeaveLog = voiceChannelLeaveLog;
    }

    public String getTicket() {
        return ticket;
    }

    public String getVote() {
        return vote;
    }

    public String getLock() {
        return lock;
    }

    public String getNameLog() {
        return nameLog;
    }

    public String getDiscriminatorLog() {
        return discriminatorLog;
    }

    public String getAvatarLog() {
        return avatarLog;
    }

    public String getStatusLog() {
        return statusLog;
    }

    public String getTypingLog() {
        return typingLog;
    }

    public String getActivityLog() {
        return activityLog;
    }

    public String getMessageDeleteLog() {
        return messageDeleteLog;
    }

    public String getMessageEditLog() {
        return messageEditLog;
    }

    public String getTextChannelUpdateLog() {
        return textChannelUpdateLog;
    }

    public String getVoiceChannelUpdateLog() {
        return voiceChannelUpdateLog;
    }

    public String getTextChannelTopicUpdateLog() {
        return textChannelTopicUpdateLog;
    }

    public String getWelcomeLog() {
        return welcomeLog;
    }

    public String getVoiceChannelJoinLog() {
        return voiceChannelJoinLog;
    }

    public String getVoiceChannelLeaveLog() {
        return voiceChannelLeaveLog;
    }
}
