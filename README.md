# 📱 Android Subscription and Bills Tracker Demo App

An Android application built using **Kotlin**, **Jetpack Compose**, and **Room Database**, following the **MVVM architecture** pattern.  
This project demonstrates modern Android app development best practices with Jetpack libraries.

---

## 🚀 Setup & Build Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/your-repo-name.git
   cd your-repo-name
Open the project in Android Studio (Giraffe/Koala or newer).

Sync Gradle dependencies.

Build and run the app on an emulator or physical device with Android 8.0 (API 26) or higher.

🏛️ Architecture
This project follows MVVM (Model-View-ViewModel) architecture:

Model → Defines data entities and repository layer (using Room Database).

View → Jetpack Compose UI components.

ViewModel → Business logic and state management (using lifecycle-viewmodel-compose + Coroutines).

📚 Libraries & Dependencies
Jetpack Compose → Declarative UI.

Lifecycle ViewModel Compose → ViewModel integration with Compose.

kotlin
Copy
Edit
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
Room Database (with KSP) → Local persistence.

kotlin
Copy
Edit
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")
Coroutines → Asynchronous operations.

kotlin
Copy
Edit
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
Navigation Compose → In-app navigation.

kotlin
Copy
Edit
implementation("androidx.navigation:navigation-compose:2.7.6")
Material Icons Extended → Richer icon set.

kotlin
Copy
Edit
implementation("androidx.compose.material:material-icons-extended:1.5.4")
✅ Completed Features
Project setup with Kotlin + Jetpack Compose.

Room Database integration for local storage.

MVVM Architecture implemented with ViewModel and Repository pattern.

Navigation Component for screen transitions.

Coroutines for async data handling.

⏳ Pending Features
Data synchronization with a remote API.

Unit & UI testing setup.

Dark mode support.

More detailed UI polish.