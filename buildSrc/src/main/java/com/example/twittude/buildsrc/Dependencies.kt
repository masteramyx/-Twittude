package com.example.twittude.buildsrc

object Versions {
    const val ktlint = "0.37.0"
    const val compose = "1.0.0-alpha12"
    const val kotlin = "1.4.30"
    const val coroutines = "1.3.9"
    const val okhttp = "4.8.1"
    const val moshi = "1.8.0"
    const val koin = "2.2.2"
    const val retrofit_core = "2.9.0"
    const val retrofit_ext = "2.4.0"
    const val twitter4j = "4.0.7"
    const val karakum = "1.4.2"
    const val timber = "4.7.1"
    const val datastore = "1.0.0-alpha01"
    const val gradle_plugin = "7.0.0-alpha06"
}

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle_plugin}"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.0.9"
    const val junit = "junit:junit:4.13"
    const val material = "com.google.android.material:material:1.1.0"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"
        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha01"

        object Compose {
            const val snapshot = ""

            const val core = "androidx.compose.ui:ui:${Versions.compose}"
            const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
            const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.compose}"
            const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
            const val runtimeLivedata =
                "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val activity = "androidx.activity:activity-compose:1.3.0-alpha02"
            const val test = "androidx.compose.test:test-core:${Versions.compose}"
            const val uiTest = "androidx.ui:ui-test:${Versions.compose}"
            const val rxjava = "androidx.compose.runtime:runtime-rxjava2:${Versions.compose}"
        }

        object Navigation {
            private const val version = "2.3.0"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    object Networking {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okhttp_logger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val moshi_adapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_core}"
        const val retrofit_rx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit_ext}"
        const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_ext}"

    }

    object Koin {
        // Koin for Kotlin
        const val koin_core = "org.koin:koin-core:${Versions.koin}"
        // Koin extended & experimental features
        const val koin_core_ext = "org.koin:koin-core-ext:${Versions.koin}"
        // Koin AndroidX Scope features
        const val koin_androidx_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        // Koin AndroidX ViewModel features
        const val koin_androidx_vm = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        // Koin AndroidX Experimental features
        const val koin_experimental = "org.koin:koin-androidx-ext:${Versions.koin}"
    }

    object Misc {
        const val twitter4j_core = "org.twitter4j:twitter4j-core:${Versions.twitter4j}"
        const val twitter4j_async = "org.twitter4j:twitter4j-async:${Versions.twitter4j}"

        const val karakum = "com.github.masteramyx:Karakum:${Versions.karakum}"
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

        const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"


    }
}