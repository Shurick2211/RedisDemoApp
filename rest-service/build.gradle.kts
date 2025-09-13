dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    val junitVersion = "5.10.2"
    val junitPlatformVersion = "1.10.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.platform:junit-platform-commons:$junitPlatformVersion")
    testImplementation("org.junit.platform:junit-platform-engine:$junitPlatformVersion")

    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.testcontainers:junit-jupiter:1.20.1")
    testImplementation("org.testcontainers:testcontainers:1.20.1")
    testImplementation("com.redis:testcontainers-redis:2.2.4")
}

tasks.test {
    useJUnitPlatform()
}