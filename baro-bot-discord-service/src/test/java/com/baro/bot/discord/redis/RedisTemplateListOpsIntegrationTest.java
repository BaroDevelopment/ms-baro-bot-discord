package com.baro.bot.discord.redis;

import com.baro.bot.discord.model.redis.MessageModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisTemplateListOpsIntegrationTest {

    @Autowired
    private ReactiveRedisTemplate<String, MessageModel> redisTemplate;
    private ReactiveListOperations<String, MessageModel> reactiveListOps;
    public static final String HASH_KEY = "MessageTest";
    private static final MessageModel MESSAGE_MODEL_1= new MessageModel("555", "555", "555", "555", "555", "555", null);
    private static final MessageModel MESSAGE_MODEL_2 = new MessageModel("111", "111", "111", "111", "111", "111", null);

    @Autowired
    private RedisConnectionFactory factory;

    @BeforeEach
    public void flush(){
        factory.getConnection().flushDb();
    }

    @BeforeAll
    public void setup() {
        reactiveListOps = redisTemplate.opsForList();
    }

    @Test
    void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        Mono<Long> lPush = reactiveListOps
                .leftPushAll(HASH_KEY, MESSAGE_MODEL_1, MESSAGE_MODEL_2)
                .log("Pushed");

        StepVerifier.create(lPush)
                .expectNext(2L)
                .verifyComplete();

        Mono<MessageModel> lPop = reactiveListOps.leftPop(HASH_KEY)
                .log("Popped");

        MessageModel message = new MessageModel(MESSAGE_MODEL_2.getMessageId(), MESSAGE_MODEL_2.getUserId(),
                MESSAGE_MODEL_2.getGuildId(), MESSAGE_MODEL_2.getChannelId(),
                MESSAGE_MODEL_2.getContent(), MESSAGE_MODEL_2.getAttachment(), null);

        StepVerifier.create(lPop)
                .expectNext(message)
                .verifyComplete();
    }

}