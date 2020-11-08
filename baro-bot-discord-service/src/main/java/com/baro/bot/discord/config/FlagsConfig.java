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

    public FlagsConfig(@Value("${ticket}") String ticket, @Value("${vote}") String vote, @Value("${lock}") String lock) {
        this.ticket = ticket;
        this.vote = vote;
        this.lock = lock;
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
}
