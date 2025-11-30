# Android SDK

This is my first attempt at creating an **Android SDK (.aar)**.  
The purpose of this SDK is to provide reusable services and functionalities that can be easily integrated into other Android applications.

## 📌 Project Purpose
- To learn how to build and package an Android SDK into an `.aar` file.  
- To expose APIs through **use cases** and **services** for modular and clean usage.  
- To create a foundation SDK that can be extended with more features.

## ⚙️ How to Build the SDK
1. Open the project in **Android Studio**.
2. Go to the library module (`SDK`).
3. Build the `.aar` file:
   - From the top menu → `Build` → `Make Module 'SDK'`. (./gradlew :SDK:assembleRelease)
   - Find the `.aar` file at:  
     ```
     app/build/outputs/aar/
     ```
4. Distribute or include this `.aar` in other Android apps.

## 📦 How to Use in Another Project
1. Copy the generated `.aar` file into your app’s `libs/` directory.
2. Add the following in your **app-level build.gradle**:
   ```gradle
   implementation files('libs/SDK-release.aar')
