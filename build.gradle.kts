import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("plugin.spring") version "1.7.10"
    id("io.gitlab.arturbosch.detekt") version "latest.release"
    id("org.jlleitschuh.gradle.ktlint") version "latest.release"
}

group = properties("group")
version = properties("version")

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    mavenCentral()
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:[1,2)")
    implementation("org.javassist:javassist:3.29.0-GA")
    compileOnly("org.slf4j:slf4j-api:1.7.36")
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testRuntimeOnly("com.h2database:h2")
}

detekt {
    buildUponDefaultConfig = true
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    withType<Test> {
        dependsOn(shadowJar)
        useJUnitPlatform()
        jvmArgs("-javaagent:${project.buildDir}/libs/${project.name}-${project.version}.jar")
    }

    jar {
        manifest {
            attributes("Agent-Class" to "com.github.stkai.jdbclogger.JdbcAgent")
            attributes("Premain-Class" to "com.github.stkai.jdbclogger.JdbcAgent")
            attributes("Can-Redefine-Classes" to true)
            attributes("Can-Retransform-Classes" to true)
        }
    }

    withType<ShadowJar> {
        dependsOn(detekt)
        minimize()
        exclude("**/*.kotlin_metadata")
        exclude("**/*.kotlin_module")
        exclude("**/*.kotlin_builtins")
        exclude("**/module_info.class")
        exclude("**/META-INF/maven/**")
        exclude("reactor/")
        relocate("org.jetbrains", "${project.group}.org.jetbrains")
        relocate("kotlin", "${project.group}.kotlin")
        relocate("javassist", "${project.group}.javassist")
        archiveClassifier.set("")
    }

    withType<Detekt> {
        jvmTarget = "1.8"
        dependsOn(ktlintFormat)
    }

    bootJar {
        enabled = false
    }
}
