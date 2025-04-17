import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mokkery.test)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kmp.nativecoroutines)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    // 🧩 XCFramework setup
    val xcf = XCFramework()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        androidMain.dependencies {
            // KTOR Engine for Android
            api(libs.ktor.client.okhttp)
        }
        androidUnitTest.dependencies {
            // Koin for Tests
            implementation(libs.koin.test)
        }
        iosMain.dependencies {
            // KTOR Engine for iOS
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            // Koin DI
            api(libs.koin.core)

            // General KTOR dependencies
            api(libs.bundles.ktor)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
}

android {
    namespace = "com.maderskitech.kmpcodingexercisenetwork"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// This check might require adjustment depending on your project type and the tasks that you use
// `endsWith("Test")` works with "*Test" tasks from Multiplafrom projects, but it does not include tasks like `check`
fun isTestingTask(name: String) = name.endsWith("Test")

val isTesting = gradle
    .startParameter
    .taskNames
    .any(::isTestingTask)

if (isTesting) allOpen {
    annotation("com.maderskitech.kmpcodingexercisenetwork.testingsupport.OpenForMokkery")
}

