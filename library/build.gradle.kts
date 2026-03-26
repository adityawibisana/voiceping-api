import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.kotlinxSerialization)
}

// 1. Set the Group ID for the project
group = "io.github.adityawibisana"
version = "0.0.1"

kotlin {
    // Proactive fix: Enforce JVM 21 globally using Kotlin toolchains
    jvmToolchain(21)

    jvm()
    androidLibrary {
        // 2. Set the Android Namespace (used for R.class generation)
        namespace = "aditya.wibisana.voicepingapi"

        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava()
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilations.configureEach {
            compilerOptions.configure {
                // Updated to JVM 21
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            implementation(libs.ktor.client.cio)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    add("androidHostTestImplementation", libs.ktor.client.cio)
}

mavenPublishing {
    publishToMavenCentral(false)
    signAllPublications()

    coordinates(
        group.toString(),
        "voiceping-api",
        version.toString()
    )

    pom {
        name = "VoicePing API"
        description = "Kotlin Multiplatform API client for VoicePing."
        inceptionYear = "2026"
        url = "https://github.com/adityawibisana/voiceping-api"
        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "adityawibisana"
                name = "Aditya Wibisana"
                url = "https://github.com/adityawibisana"
            }
        }
        scm {
            url = "https://github.com/adityawibisana/voiceping-api"
            connection = "scm:git:git://github.com/adityawibisana/voiceping-api.git"
            developerConnection = "scm:git:ssh://git@github.com/adityawibisana/voiceping-api.git"
        }
    }
}

