pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()

        val frcYear: String by settings
        val os = System.getProperty("os.name").toLowerCase()

        // Windows: C:\Users\Public\wpilib\2023
        val frcHome = if (os.startsWith("win")) {
            val publicFolder = System.getenv("PUBLIC") ?: "C:\\Users\\Public"
            val homeRoot = File(publicFolder, "wpilib")
            File(homeRoot, frcYear)
        } else {
            val userFolder = System.getProperty("user.home")
            val homeRoot = File(userFolder, "wpilib")
            File(homeRoot, frcYear)
        }

        // Add maven repo
        maven {
            name = "frcHome"
            url = File(frcHome, "maven").toURI()
        }
    }
}

includeBuild("build-logic")
include("framework")
include("robot")