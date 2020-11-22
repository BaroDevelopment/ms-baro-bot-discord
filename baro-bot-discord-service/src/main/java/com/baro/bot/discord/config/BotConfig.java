package com.baro.bot.discord.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfig {

    private final String token;
    private final String activity;
    private final String prefix;
    private final String googleApiKey;
    private final String googleEngineId;
    private final String oxfordDictionaryAppId;
    private final String oxfordDictionaryApiKey;
    private final int redisMessageCacheDurationMinutes;
    private final List<String> botOwnerIds;

    public BotConfig(
            @Value("${token}") String token,
            @Value("${activity}") String activity,
            @Value("${redis.message.cache.duration.minutes}") int redisMessageCacheDurationMinutes,
            @Value("${prefix}") String prefix,
            @Value("${google.search.api.key}") String googleApiKey,
            @Value("${google.search.engine.id}") String googleEngineId,
            @Value("${oxford.dictionary.app.id}") String oxfordDictionaryAppId,
            @Value("${oxford.dictionary.api.key}") String oxfordDictionaryApiKey,
            @Value("${owner.ids}") List<String> botOwnerIds) {
        this.token = token;
        this.activity = activity;
        this.prefix = prefix;
        this.botOwnerIds = botOwnerIds;
        this.googleApiKey = googleApiKey;
        this.googleEngineId = googleEngineId;
        this.oxfordDictionaryAppId = oxfordDictionaryAppId;
        this.oxfordDictionaryApiKey = oxfordDictionaryApiKey;
        this.redisMessageCacheDurationMinutes = redisMessageCacheDurationMinutes;
    }

    public String getToken() {
        return token;
    }

    public String getActivity() {
        return activity;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getRedisMessageCacheDurationMinutes() {
        return redisMessageCacheDurationMinutes;
    }

    public List<String> getBotOwnerIds() {
        return botOwnerIds;
    }

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public String getGoogleEngineId() {
        return googleEngineId;
    }

    public String getOxfordDictionaryAppId() {
        return oxfordDictionaryAppId;
    }

    public String getOxfordDictionaryApiKey() {
        return oxfordDictionaryApiKey;
    }
}
