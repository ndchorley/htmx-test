plugins {
    kotlin("jvm") version "2.1.20"
    id("application")
    id("com.gradleup.shadow") version "9.0.0-beta12"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:6.6.0.1"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-template-handlebars")
}


kotlin {
    jvmToolchain(23)
}

application {
    mainClass = "org.example.MainKt"
}
