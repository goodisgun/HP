package com.example.exercise.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
@EnableCaching
public class CacheConfig {


  @Bean
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
    RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
        .disableCachingNullValues()  // 캐시 값이 null인경우 캐싱하지 않도록 설정
        .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC)) // 만료값 기본 60초 세팅
        .computePrefixWith(CacheKeyPrefix.simple())
        // 레디스에 저장하는 키 값을 만들때 사용하는 Prefix를 지정하는 설정 => 여러개 애플리케이션에서 레디스를 사용할때 키값 충돌을 막기위해사용
        // redis패키지 CacheKey를 활용하는데 사용됨
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))  // 레디스에 저장될 문자열 직렬화 하는데 사용
        .serializeValuesWith(RedisSerializationContext
            .SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer()));  // 객체 => Json문자열로 직렬화 하고 다시 역직렬화 할때 다시 객체로 변환하는데 사용
            // 객체타입의 데이터를 캐싱하기 위해서 Redis문자열 타입 데이터를 객체로 변환하는 과정이 필요하기 때문!
            // => 객체를 쉽게 캐싱하고 조회할 수 있게 하기 위해 직렬화 => 역직렬화 사용


    return RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();

  }
}
