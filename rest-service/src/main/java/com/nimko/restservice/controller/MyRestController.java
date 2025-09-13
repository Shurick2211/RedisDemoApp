package com.nimko.restservice.controller;

import com.nimko.restservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
public class MyRestController {

  private final RedisService redisService;

  @PostMapping
  public ResponseEntity<String> postMessage(@RequestBody final String message) {
    log.info("{}.postMessage: {}", getClass().getSimpleName(), message);
    return ResponseEntity.of(redisService.processMessage(message));
  }

}
