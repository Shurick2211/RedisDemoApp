#!/bin/bash
set -e

echo "=== 1. Сборка Gradle проекта ==="
./gradlew :process-service:bootJar
./gradlew :rest-service:bootJar

echo "=== 2. Сборка Docker-образов ==="
docker build -t ghcr.io/shurick2211/redisdemoapp/rest-service:latest rest-service
docker build -t ghcr.io/shurick2211/redisdemoapp/process-service:latest process-service

echo "=== 3. Старт тестов (test) ==="
./gradlew :rest-service:test

