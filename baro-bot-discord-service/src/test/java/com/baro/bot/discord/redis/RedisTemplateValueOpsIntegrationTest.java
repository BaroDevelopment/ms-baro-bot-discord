package com.baro.bot.discord.redis;

import com.baro.bot.discord.model.redis.MessageModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.Duration;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisTemplateValueOpsIntegrationTest {

    @Autowired
    private ReactiveRedisTemplate<String, MessageModel> redisTemplate;
    private ReactiveValueOperations<String, MessageModel> reactiveValueOps;

    private static final MessageModel MESSAGE_MODEL_VALUE_1 = new MessageModel("555", "555", "555", "555", "555", "555", null);
    private static final String MESSAGE_MODEL_KEY_1 = "555";

    private static final MessageModel MESSAGE_MODEL_VALUE_2 = new MessageModel("111", "111", "111", "111", "111", "111", null);
    private static final String MESSAGE_MODEL_KEY_2 = "111";

    @Autowired
    private RedisConnectionFactory factory;

    @BeforeAll
    public void setup() {
        reactiveValueOps = redisTemplate.opsForValue();
    }

    @Test
    void givenMessageModel_whenSet_thenSet() {

        Mono<Boolean> result = reactiveValueOps.set(MESSAGE_MODEL_KEY_1, MESSAGE_MODEL_VALUE_1);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void givenMessageModelId_whenGet_thenReturnsMessageModel() {

        Mono<MessageModel> fetchedMessages = reactiveValueOps.get(MESSAGE_MODEL_KEY_1);

        MessageModel message = new MessageModel(MESSAGE_MODEL_VALUE_1.getMessageId(), MESSAGE_MODEL_VALUE_1.getUserId(),
                MESSAGE_MODEL_VALUE_1.getGuildId(), MESSAGE_MODEL_VALUE_1.getChannelId(),
                MESSAGE_MODEL_VALUE_1.getContent(), MESSAGE_MODEL_VALUE_1.getAttachment() , null);

        StepVerifier.create(fetchedMessages)
                .expectNext(message)
                .verifyComplete();
    }

    @Test
    void givenMessageModel_whenSetWithExpiry_thenSetsWithExpiryTime() throws InterruptedException {

        Mono<Boolean> result = reactiveValueOps.set(MESSAGE_MODEL_KEY_2, MESSAGE_MODEL_VALUE_2, Duration.ofSeconds(5));

        Mono<MessageModel> fetchedMessages = reactiveValueOps.get(MESSAGE_MODEL_KEY_2);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        Thread.sleep(6000L);

        StepVerifier.create(fetchedMessages)
                .expectNextCount(0L)
                .verifyComplete();
    }

}