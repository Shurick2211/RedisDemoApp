#!/bin/bash
set -e

echo "=== 1. Сборка Gradle проекта ==="
./gradlew clean build -x test

echo "=== 2. Сборка Docker-образов ==="
docker build -t ghcr.io/shurick2211/redisdemoapp/rest-service:latest rest-service
docker build -t ghcr.io/shurick2211/redisdemoapp/process-service:latest process-service

echo "=== 3. Запуск тестов (test-service) ==="
./gradlew :test-service:test

echo "=== 4. Если тесты упали, отчет тут: test-service/build/reports/tests/test/index.html ==="
