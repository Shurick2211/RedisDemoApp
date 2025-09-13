package com.nimko.testservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;


class TestServiceApplicationTests {

  @Test
  void testFlow() {
    final Network network = Network.newNetwork();

    try (final RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:latest"))
        .withNetwork(network)
        .withNetworkAliases("redis")) {
      redis.start();

      try (final GenericContainer<?> process = new GenericContainer<>("ghcr.io/shurick2211/process-service:latest")
          .withNetwork(network)
          .dependsOn(redis)
          .withEnv("REDIS_HOST", "redis")) {
        process.start();

        try (final GenericContainer<?> rest = new GenericContainer<>("ghcr.io/shurick2211/rest-service:latest")
            .withExposedPorts(8081)
            .withNetwork(network)
            .dependsOn(redis, process)
            .withEnv("REDIS_HOST", "redis")) {
          rest.start();

          final String url = "http://" + rest.getHost() + ":" + rest.getMappedPort(8081) + "/send";
          final RestTemplate restTemplate = new RestTemplate();

          final String response = restTemplate.postForObject(url, "hello redis", String.class);
          assertThat(response).isEqualTo("HELLO REDIS");
        }
      }
    }
  }

}
