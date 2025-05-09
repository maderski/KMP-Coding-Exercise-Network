# KMP-Coding-Exercise-Network
Kotlin Mutliplatform Network Library for a coding exercise

This is a networking library that can be used with Android as an AAR or with iOS as a Framework.  Since this is only an exercise I did not setup publishing for this project.  To get the AAR or Framework, the project will need to be cloned and then assemble the artifact.

Most of the files are located in the commonMain module, since the majority of the code is shared.  I also demonstrated a few tests in the commonTest module.  The only platform specific code in the project is related to the HttpClient due to the platform specific engines.  Android is using Okhttp and iOS is using Darwin.  This code can be found in androidMain and iosMain modules.  

## Android
*Note: The dependencies required by the KMP Network Library also have to be setup in the Android Project.  Not ideal, but since this was an exercise and I am using an AAR for KMP demonstration purposes I'm bypassing the Gradle's metadata (the POM), so none of the api scoped libraries that was declared get pulled in transitiviely. 

To use on Android this library in your Android project:  
1) Download the shared-release.aar and add it to your libs folder in your Android project.
2) Then under dependencies add:
```
    implementation(files("libs/shared-release.aar"))
```
4) Add the Koin and Ktor dependencies:
In the libs.versions file under [libraries]
```
    # Koin DI
    koin-android = { module = "io.insert-koin:koin-android", version.ref = "koin" }
    koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koin" }
    
    # Ktor
    ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
    ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
    ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
    ktor-client-auth = { module = "io.ktor:ktor-client-auth", version.ref = "ktor" }
    ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
```
In the app build gradle
```
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // KTOR Engine for Android
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)
```
5) Add the Kotlin serialization plugin under [plugins]
```
    # Serialization
    kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
```
6) Add the kotlin serialization in the app build gradle under plugins
```
    alias(libs.plugins.kotlin.serialization)
```
7) Add the koltin serialization in the project build gradle under plugins
```
    alias(libs.plugins.kotlin.serialization).apply(false)
```
8) Setup Koin in onCreate in your Application class
```
    startKoin {
          androidContext(this@MainApplication)
          androidLogger()
          modules(appModule + commonModule)
        }
```
9) In your Manifest add the Internet permission
```
    <uses-permission android:name="android.permission.INTERNET" />
```
10) And to use, just inject the itemsRepository and call getAllItems()
```
    fun getItems() {
        viewModelScope.launch {
            itemsRepository.getAllItems()
                .onSuccess { listIdToItemsMap ->
                    // Do something..
                }
                .onFailure { throwable ->
                    // Handle error
                }
          }
      }
```

An example of using this library in a Native Android Application can be found here: [Android App Repo Link](https://github.com/maderski/Android-Coding-Exercise-App)


## iOS
To use this library in an iOS project:
1) Download the shared-release.xcframework and add it to your framework folder in your iOS project project.
2) General -> Targets -> Frameworks, Libraries, Embedded Content add the shared.xcframework
3) Add the Native Coroutines dependency
[KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines)
4) In your App file add:
```
import shared

init() {
        KoinHelper.shared.doInitKoin(appModules: [])
    }
```
5) And to use in the viewModel you would like to retrieve the items
```
import shared
import KMPNativeCoroutinesAsync

private let itemRepository = KoinHelper.shared.getItemRepositoryWrapper()

func fetchData() async {
            Task { @MainActor in
                let result = await asyncResult(for: itemRepository.getAllItems())
                
                switch result {
                case .success(let data):
                    // Do something..
                    
                case .failure(let error):
                    // Handle error
                }
            }
        }
```
An example of using this library in a Native iOS Application can be found here: [iOS App Repo Link](https://github.com/maderski/iOS-Coding-Exercise-App)
