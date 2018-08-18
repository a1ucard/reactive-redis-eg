package com.a1ucard.reactive_redis_eg.config;

import com.a1ucard.reactive_redis_eg.serializer.ObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    /* !!! Method name must exactly override reactiveRedisConnectionFactory. Otherwise, you will hit 2 redis connection established exception*/
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory("192.168.1.71", 6379);
    }

    @Bean
    public ReactiveRedisTemplate<String, Object> genericHashOperation() {
        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
                .<String, Object>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new ObjectSerializer<Object>())
                .build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(), serializationContext);
    }

}
