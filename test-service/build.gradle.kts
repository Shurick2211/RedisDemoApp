import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.testcontainers:junit-jupiter:1.20.1")
    testImplementation("org.testcontainers:testcontainers:1.20.1")
    testImplementation("com.redis:testcontainers-redis:2.2.4")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}