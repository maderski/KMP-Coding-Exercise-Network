import org.gradle.kotlin.dsl.testImplementation
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
            implementation(libs.ktor.client.okhttp)
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
            implementation(libs.bundles.ktor)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
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
