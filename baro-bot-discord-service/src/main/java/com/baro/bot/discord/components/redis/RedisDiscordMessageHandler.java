package com.baro.bot.discord.components.redis;

import com.baro.bot.discord.config.BotConfig;
import com.baro.bot.discord.model.redis.MessageModel;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Component
public class RedisDiscordMessageHandler {

    private ReactiveRedisTemplate<String, MessageModel> redisTemplate;
    private ReactiveValueOperations<String, MessageModel> reactiveValueOps;
    private BotConfig botConfig;

    public RedisDiscordMessageHandler(ReactiveRedisTemplate<String, MessageModel> redisTemplate, BotConfig botConfig) {
        this.redisTemplate = redisTemplate;
        this.reactiveValueOps = redisTemplate.opsForValue();
        this.botConfig = botConfig;
    }

    public void save(Message message) {
        // we don't store embed messages
        if (!message.getEmbeds().isEmpty()) return;

        String imageUrl = message.getAttachments().isEmpty() ? "" : message.getAttachments().get(0).getUrl();
        MessageModel messageModel = new MessageModel(message.getId(), message.getAuthor().getId(),
                message.getGuild().getId(), message.getChannel().getId(), message.getContentDisplay(), imageUrl,
                message.getTimeCreated().toString());
        Mono<Boolean> result = reactiveValueOps.set(message.getId(), messageModel,
                Duration.ofMinutes(botConfig.getRedisMessageCacheDurationMinutes()));
        result.subscribe();
    }

    public MessageModel findOne(String messageId){
        return reactiveValueOps.get(messageId).block();
    }
}
