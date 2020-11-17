package com.baro.bot.discord.redis;

import com.baro.bot.discord.model.redis.MessageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.ByteBuffer;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * We can use ReactiveStringCommands to store multiple keys with a single invocation,
 * basically invoking the SET command multiple times.
 * And then, we can retrieve those keys through ReactiveKeyCommands, invoking the KEYS command:
 * Finally, String and Key Commands are just two among many command interfaces that Spring Data Redis exposes reactively.
 * https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/connection/package-summary.html
 */
class RedisKeyCommandsIntegrationTest {

    @Autowired
    private ReactiveKeyCommands keyCommands;

    @Autowired
    private ReactiveStringCommands stringCommands;

    @Autowired
    private RedisConnectionFactory factory;

    @BeforeEach
    public void flush(){
        factory.getConnection().flushDb();
    }

    @Test
    void givenFluxOfKeys_whenPerformOperations_thenPerformOperations() {
        Flux<String> keys = Flux.just("key1", "key2", "key3", "key4");

        Flux<ReactiveStringCommands.SetCommand> generator = keys.map(String::getBytes)
                .map(ByteBuffer::wrap)
                .map(key -> ReactiveStringCommands.SetCommand.set(key).value(key));

        StepVerifier.create(stringCommands.set(generator))
                .expectNextCount(4L)
                .verifyComplete();

        Mono<Long> keyCount = keyCommands.keys(ByteBuffer.wrap("key*".getBytes()))
                .flatMapMany(Flux::fromIterable)
                .count();

        StepVerifier.create(keyCount)
                .expectNext(4L)
                .verifyComplete();
    }

}