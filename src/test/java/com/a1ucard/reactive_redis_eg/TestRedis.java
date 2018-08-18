package com.a1ucard.reactive_redis_eg;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestRedis {

    @Autowired
    private ReactiveRedisTemplate<String, Object> genericHashOperation;

    @Test
    public void testRedis() {

        Mono.just("value1")
                .flatMap(v->genericHashOperation.opsForHash().put("user1","sessionKey", v))
                .thenMany(genericHashOperation.keys("*")
                    .flatMap(key->genericHashOperation.opsForHash().get(key,"sessionKey")))
                .subscribe(v->Assert.assertEquals(v,"value1"));



    }

}
