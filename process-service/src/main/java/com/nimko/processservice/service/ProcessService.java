package com.nimko.processservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessService {

  @Value("${redis.requests}")
  private String requests;

  @Value("${redis.responses}")
  private String responses;

  private final RedissonClient redisson;
  private final ExecutorService executor = Executors.newFixedThreadPool(2);

  @PostConstruct
  public void start() {
    executor.submit(() -> {
      final RBlockingQueue<String> requestQueue = redisson.getBlockingQueue(requests);
      while (true) {
        try {
          final String req = requestQueue.take();
          executor.submit(() -> process(req));
        } catch (final InterruptedException e) {
          log.error("{}.start() - Interrupted thread:", getClass().getSimpleName(), e);
          Thread.currentThread().interrupt();
          break;
        }
      }
    });
  }

  private void process(final String req) {
    try {
      final String[] parts = req.split("\\|", 2);
      final String id = parts[0];
      final String msg = parts[1];
      final String result = msg.toUpperCase();

      final RBlockingQueue<String> responseQueue = redisson.getBlockingQueue(responses + ":" + id);
      responseQueue.put(result);
    } catch (final Exception e) {
      log.error("{}.process() - Error processing request", getClass().getSimpleName(), e);
    }
  }

}
