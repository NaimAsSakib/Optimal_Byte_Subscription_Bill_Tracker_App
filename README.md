# 📱 Android Subscription and Bills Tracker Demo App

An Android application built using **Kotlin**, **Jetpack Compose**, and **Room Database**, following the **MVVM architecture** pattern.  
This project demonstrates modern Android app development best practices with Jetpack libraries.

---

## 🚀 Setup & Build Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/NaimAsSakib/Optimal_Byte_Subscription_Bill_Tracker_App.git

Open the project in Android Studio (Latest Version).

Sync Gradle dependencies.

Build and run the app on an emulator or physical device.

🏛️ Architecture
This project follows MVVM (Model-View-ViewModel) architecture:

Model → Defines data entities and repository layer (using Room Database).

View → Jetpack Compose UI components.

ViewModel → Business logic and state management (using lifecycle-viewmodel-compose + Coroutines).

📚 Libraries & Dependencies
Jetpack Compose → Declarative UI.

Lifecycle ViewModel Compose → ViewModel integration with Compose.
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
Room Database (with KSP) → Local persistence.
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")
Coroutines → Asynchronous operations.
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
Navigation Compose → In-app navigation.
implementation("androidx.navigation:navigation-compose:2.7.6")
Material Icons Extended → Richer icon set.
implementation("androidx.compose.material:material-icons-extended:1.5.4")


✅ Completed Features
Project setup with Kotlin + Jetpack Compose.

Room Database integration for local storage.

MVVM Architecture implemented with ViewModel and Repository pattern.

Coroutines for async data handling.

Dark Mode handled

Currency conversion API called and converted all currencies to BDT

⏳ Pending Features
Unit Testing

Unit & UI testing setup.

More detailed UI polish.