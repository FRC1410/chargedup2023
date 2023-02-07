plugins {
    id("org.frc1410.tooling.build.java") apply false
    id("edu.wpi.first.GradleRIO") version "2023.2.1" apply false
}

allprojects {
    group = "org.frc1410"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.frc1410.tooling.build.java")
}