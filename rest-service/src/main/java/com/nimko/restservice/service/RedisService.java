package com.nimko.restservice.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

  private final RedissonClient redissonClient;

  public Optional<String> processMessage(final String message) {
    final String messId = UUID.randomUUID().toString();
    try {
      final RBlockingQueue<String> requestQueue = redissonClient.getBlockingQueue("requests");
      requestQueue.put(messId + "|" + message);

      final RBlockingQueue<String> responseQueue = redissonClient.getBlockingQueue(
          "responses:" + messId);
      final String response = responseQueue.poll(10, TimeUnit.SECONDS);
      log.info("{}.processMessage() response: {}", getClass().getSimpleName(), response);
      return Optional.ofNullable(response);
    } catch (final InterruptedException e) {
      log.info("{}.processMessage() Error:", getClass().getSimpleName(), e);
      Thread.currentThread().interrupt();
    }
    return Optional.empty();
  }
}
