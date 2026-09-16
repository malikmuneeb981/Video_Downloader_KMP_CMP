# 📥 Downloader KMP

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.10-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.11.1-purple.svg?logo=jetpackcompose)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg?logo=android)](https://www.android.com)
[![iOS](https://img.shields.io/badge/Platform-iOS-lightgrey.svg?logo=apple)](https://developer.apple.com/ios/)

> A powerful, cross-platform video downloader and media player built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform** for **Android & iOS**. Easily download videos via links, save photo & video statuses, explore trending reels, and enjoy seamless offline playback with an integrated HD player and file manager.

---

## ✨ Features

- 🚀 **Universal Video Downloader**: Download high-quality videos and clips from supported social platforms and websites via URL.
- 💬 **Status Saver**: Save, view, and organize WhatsApp/social media statuses directly to local storage.
- 🎬 **Built-in HD Video Player**: Offline media player with playback controls, folder browsing, and high-resolution rendering.
- 📱 **Trending Reels & Video Feeds**: Discover curated feeds, trending videos, and short-form reels.
- 📁 **Organized File Manager**: Organize downloaded media into dedicated folders, with quick sharing, renaming, and deletion.
- 🌐 **Multilingual Support**: In-app language switching with support for multiple global languages.
- 🎨 **100% Shared UI**: Modern, responsive UI built completely with Compose Multiplatform and Material 3 design.

---

## 🛠️ Tech Stack & Architecture

This project follows **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** design pattern.

- **Cross-Platform Engine**: [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html)
- **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Material 3)
- **Dependency Injection**: [Koin](https://insert-koin.io/) (Koin Multiplatform + ViewModel)
- **Networking**: [Ktor Client 3](https://ktor.io/) (OkHttp engine on Android, Darwin engine on iOS)
- **Image & Video Loading**: [Coil 3](https://coil-kt.github.io/coil/) (Multiplatform image & video frame loading)
- **Media Playback**: Media3 ExoPlayer on Android & Native AVPlayer on iOS
- **Local Persistence**: AndroidX DataStore Preferences
- **Serialization**: Kotlinx Serialization JSON
- **Navigation**: Jetpack Navigation Compose Multiplatform

---

## 📁 Project Structure

```
DownloaderKMPProductionApp/
├── androidApp/          # Native Android application entry point & Manifest
├── iosApp/              # Native iOS project (SwiftUI wrapper / Xcode workspace)
├── shared/              # Shared multiplatform code
│   └── src/
│       ├── commonMain/  # 100% shared business logic, domain models, viewmodels & UI
│       │   └── kotlin/org/example/project/
│       │       ├── commons/         # Common utilities & helpers
│       │       ├── data/            # API services, network models, repositories
│       │       ├── di/              # Koin dependency injection modules
│       │       ├── domain/          # Use cases, domain models, repository interfaces
│       │       ├── navigation/      # Compose Multiplatform navigation graph & routes
│       │       └── presentation/    # Composable screens, components, viewmodels
│       ├── androidMain/ # Android-specific implementations (Status saver, ExoPlayer)
│       └── iosMain/     # iOS-specific implementations (Status saver, AVPlayer)
└── gradle/              # Version catalogs (libs.versions.toml) & Gradle wrapper
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Ladybug or newer with the **Kotlin Multiplatform Mobile** plugin installed.
- **Xcode** 15+ (for running the iOS application).
- **JDK 17** or newer.

### Configuration

1. **Clone the repository**:
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
   cd <your-repo-name>
   ```

2. **Setup `local.properties`**:
   Copy the provided example file to create your `local.properties`:
   ```bash
   cp local.properties.example local.properties
   ```
   Open `local.properties` and provide your Android SDK path and API keys:
   ```properties
   sdk.dir=/path/to/your/Android/sdk
   BASE_URL=https://your-api-domain.com/
   SECRET_KEY=your_secret_key_here
   ENDPOINT_CHECKER=endpointchecker/
   ```

---

## 🏃 Running the Project

### Android
You can run the Android app via Android Studio run configuration or command line:
```bash
./gradlew :androidApp:installDebug
```

### iOS
1. Open the `iosApp` directory in Xcode:
   ```bash
   open iosApp/iosApp.xcworkspace # or iosApp.xcodeproj
   ```
2. Select an iOS simulator or physical device and click **Run** (`Cmd + R`).

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.