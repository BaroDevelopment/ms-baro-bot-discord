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
    private static final MessageModel MESSAGE_MODEL_1= new MessageModel("555", "555");
    private static final MessageModel MESSAGE_MODEL_2 = new MessageModel("111", "111");

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
    public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        Mono<Long> lPush = reactiveListOps
                .leftPushAll(HASH_KEY, MESSAGE_MODEL_1, MESSAGE_MODEL_2)
                .log("Pushed");

        StepVerifier.create(lPush)
                .expectNext(2L)
                .verifyComplete();

        Mono<MessageModel> lPop = reactiveListOps.leftPop(HASH_KEY)
                .log("Popped");

        StepVerifier.create(lPop)
                .expectNext(new MessageModel(MESSAGE_MODEL_2.getMessageId(), MESSAGE_MODEL_2.getContent()))
                .verifyComplete();
    }

}