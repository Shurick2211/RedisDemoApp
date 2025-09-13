package com.nimko.processservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

  @Bean
  public RedissonClient redissonClient() {
    final Config cfg = new Config();
    final String redisHost = System.getenv().getOrDefault("REDIS_HOST", "localhost");
    cfg.useSingleServer().setAddress("redis://" + redisHost + ":6379");
    return Redisson.create(cfg);
  }
}
