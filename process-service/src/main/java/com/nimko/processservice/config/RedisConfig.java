package com.nimko.processservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

  @Value("${redis.port}")
  private int port;

  @Value("${redis.host}")
  private String host;

  @Bean
  public RedissonClient redissonClient() {
    final Config cfg = new Config();
    final String redisHost = System.getenv().getOrDefault("REDIS_HOST", host);
    cfg.useSingleServer().setAddress("redis://" + redisHost + ":" +  port);
    return Redisson.create(cfg);
  }
}
