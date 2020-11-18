package com.baro.bot.discord.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfig {

    private final String token;
    private final String activity;
    private final String prefix;
    private final int redisMessageCacheDurationMinutes;
    private final List<String> botOwnerIds;

    public BotConfig(
            @Value("${token}") String token,
            @Value("${activity}") String activity,
            @Value("${redis.message.cache.duration.minutes}") int redisMessageCacheDurationMinutes,
            @Value("${prefix}") String prefix,
            @Value("${owner.ids}") List<String> botOwnerIds) {
        this.token = token;
        this.activity = activity;
        this.prefix = prefix;
        this.botOwnerIds = botOwnerIds;
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
}
