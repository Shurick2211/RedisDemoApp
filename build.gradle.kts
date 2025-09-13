plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}



allprojects {
    group = "com.nimko"
    version = "0.0.1-SNAPSHOT"
    description = "RedisDemoApp"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply( plugin ="java")
    apply (plugin = "org.springframework.boot")
    apply (plugin = "io.spring.dependency-management")


    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.redisson:redisson:3.36.0")
        compileOnly ("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


