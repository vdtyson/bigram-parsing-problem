import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
}

group = "com.versilistyson"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

sourceSets.getByName("main") {
    java.srcDir("src/main/kotlin")
}

sourceSets.getByName("test") {
    java.srcDir("src/test/kotlin")
}

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.1")
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
