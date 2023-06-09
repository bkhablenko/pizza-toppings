import org.gradle.api.tasks.wrapper.Wrapper.DistributionType

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.spring") version "1.8.20"
    kotlin("plugin.jpa") version "1.8.20"
}

group = "com.github.bkhablenko"
version = "0.1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.uuid:java-uuid-generator:4.1.0")
    implementation("net.logstash.logback:logstash-logback-encoder:7.3")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

tasks {
    clean {
        doLast {
            project.delete("postgresql")
        }
    }
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xemit-jvm-type-annotations", "-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
    test {
        useJUnitPlatform()
    }
    wrapper {
        distributionType = DistributionType.ALL
        version = "8.1"
    }
}
