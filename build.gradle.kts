// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${com.example.twittude.buildsrc.Versions.gradle_plugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${com.example.twittude.buildsrc.Versions.kotlin}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven { url = uri("https://jitpack.io") }
        google()
        jcenter()

    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}