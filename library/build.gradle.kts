import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlinxSerialization)
}

// 1. Set the Group ID for the project
group = "aditya.wibisana"
version = "1.0.0"

kotlin {
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
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktorfit.lib)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
    add("kspAndroid", libs.ktorfit.ksp)
    add("kspJvm", libs.ktorfit.ksp)
    add("kspIosX64", libs.ktorfit.ksp)
    add("kspIosArm64", libs.ktorfit.ksp)
    add("kspIosSimulatorArm64", libs.ktorfit.ksp)
    add("kspLinuxX64", libs.ktorfit.ksp)
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    // 3. Set the final artifact coordinate: aditya.wibisana:voicepingapi:1.0.0
    coordinates(group.toString(), "voicepingapi", version.toString())

    pom {
        name = "VoicePing API"
        description = "Kotlin Multiplatform API client for VoicePing."
        inceptionYear = "2026"
        url = "https://github.com/adityawibisana/voiceping-api" // Update this to your real repo
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

afterEvaluate {
    tasks.findByName("extractAndroidMainAnnotations")?.apply {
        dependsOn("kspCommonMainKotlinMetadata")
        dependsOn("kspAndroidMain")
    }
}