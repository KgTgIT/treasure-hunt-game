import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    kotlin("plugin.allopen") version "1.3.61"
    id("groovy")
    id("idea")
}

group = "it.kgtg"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val developmentOnly: Configuration by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
    maven("https://jcenter.bintray.com")
}

val micronautVersion = "1.3.0"
val micronautSpringVersion = "1.0.2"

kapt {
    includeCompileClasspath = false
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:$micronautVersion")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")

    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.springframework.boot:spring-boot-starter-thymeleaf")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-validation")
    runtimeOnly("io.micronaut:micronaut-views")
    runtimeOnly("io.micronaut.configuration:micronaut-hibernate-validator")
    runtimeOnly("io.micronaut.spring:micronaut-spring-web")
    runtimeOnly("io.micronaut.spring:micronaut-spring-boot")
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut.configuration:micronaut-openapi")
    kapt("io.micronaut.spring:micronaut-spring-boot-annotation")
    kapt("io.micronaut.spring:micronaut-spring-web-annotation")

    implementation("io.swagger.core.v3:swagger-annotations")

    testImplementation("io.micronaut.test:micronaut-test-spock")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito", module = "mockito-core")
    }
    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
    testImplementation("com.athaydes:spock-reports:1.6.3")
    testImplementation("net.bytebuddy:byte-buddy:1.10.7")

    kaptTest("io.micronaut.spring:micronaut-spring-web-annotation")
    kaptTest("io.micronaut:micronaut-inject-java")
    kaptTest("io.micronaut:micronaut-inject-groovy")
}

tasks {
    "bootRun"(BootRun::class) {
        args("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
            jvmTarget = "1.8"
            // Retain parameter names for Java reflection
            javaParameters = true
        }
    }
}
