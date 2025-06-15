# 🚀 Developer Onboarding Guide

## Welcome to WorkFun Authentication App!

This guide will help you get up and running with the WorkFun co-working space booking application. This is an Android app built with modern Android development practices using Kotlin and Jetpack Compose.

## 📋 Prerequisites

Before you start, make sure you have:

- **Android Studio** (Latest stable version recommended)
- **JDK 11** or higher
- **Git** for version control
- **Android SDK** with API level 34+ (configured in Android Studio)
- Basic knowledge of:
  - Kotlin programming language
  - Jetpack Compose
  - Android development fundamentals
  - MVVM architecture pattern

## 🏗️ Project Overview

### What is WorkFun?
WorkFun is a co-working space booking application that allows users to:
- Browse and book co-working spaces
- Manage their user profile and membership
- View news and updates
- Track their booking history

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Navigation**: Navigation Compose
- **Dependency Injection**: Manual DI with Factory pattern
- **Build System**: Gradle with Kotlin DSL

## 🚀 Getting Started

### 1. Clone and Setup

```bash
# Clone the repository
git clone <repository-url>
cd tugas11authentication

# Open in Android Studio
# File -> Open -> Select the project folder
```

### 2. Project Structure

```
app/src/main/java/com/example/tugas_11_authentication/
├── MainActivity.kt                 # Main entry point
├── data/                          # Data layer
│   ├── converter/
│   │   └── DateConverter.kt       # Room type converters
│   ├── dao/
│   │   └── UserDao.kt            # Database access objects
│   ├── database/
│   │   └── AppDatabase.kt        # Room database configuration
│   └── entity/
│       └── User.kt               # Database entities
├── navigation/
│   └── AppNavigation.kt          # Navigation setup
├── repository/
│   └── AuthRepository.kt         # Business logic layer
├── ui/
│   ├── screens/                  # Compose screens
│   │   ├── OnboardingScreen.kt
│   │   ├── LoginScreen.kt
│   │   ├── RegisterScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── BookingScreen.kt
│   │   └── UserScreen.kt
│   └── theme/                    # App theming
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── viewmodel/
    ├── AuthViewModel.kt          # Authentication state management
    └── ViewModelFactory.kt       # ViewModel dependency injection
```

### 3. Key Dependencies

The project uses these main dependencies (defined in `gradle/libs.versions.toml`):

```toml
# Core Android
androidx-core-ktx = "1.16.0"
androidx-lifecycle-runtime-ktx = "2.9.1"
androidx-activity-compose = "1.10.1"

# Compose
compose-bom = "2024.09.00"
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }

# Room Database
room = "2.6.1"

# Navigation
navigation = "2.8.0"

# ViewModel
lifecycle-viewmodel = "2.8.6"
```

## 🏛️ Architecture Deep Dive

### MVVM Pattern Implementation

1. **Model (Data Layer)**
   - `User.kt`: Entity representing user data
   - `UserDao.kt`: Database operations
   - `AppDatabase.kt`: Room database configuration
   - `AuthRepository.kt`: Business logic and data management

2. **View (UI Layer)**
   - Jetpack Compose screens in `ui/screens/`
   - Material 3 design system
   - Reactive UI that observes ViewModel state

3. **ViewModel**
   - `AuthViewModel.kt`: Manages authentication state
   - Handles user interactions and business logic
   - Exposes UI state through Compose State

### Database Schema

```kotlin
@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val fullName: String,
    val dateOfBirth: Date
)
```

## 🔧 Development Workflow

### 1. Running the App

1. Open Android Studio
2. Wait for Gradle sync to complete
3. Select a device/emulator (API 34+)
4. Click "Run" or press `Shift + F10`

### 2. App Flow

1. **Onboarding**: 3-screen introduction to the app
2. **Authentication**: Login/Register with email and password
3. **Home**: User dashboard with news feed and booking access
4. **Booking**: Browse available co-working spaces
5. **Profile**: User information and member card

### 3. Key Features to Understand

#### Authentication System
- Email-based registration and login
- Password validation and confirmation
- Session persistence using Room database
- Proper logout functionality

#### Navigation
- Bottom navigation for main screens
- Authentication flow handling
- Deep linking support

#### Data Persistence
- Room database for user data
- Type converters for Date objects
- Repository pattern for data access

## 🛠️ Common Development Tasks

### Adding a New Screen

1. Create a new Composable function in `ui/screens/`
2. Add navigation route in `AppNavigation.kt`
3. Update ViewModel if needed for state management
4. Add any required database entities/DAOs

### Modifying the Database

1. Update entity classes in `data/entity/`
2. Modify DAO interfaces in `data/dao/`
3. Increment database version in `AppDatabase.kt`
4. Add migration strategy if needed

### Adding New Dependencies

1. Update `gradle/libs.versions.toml`
2. Add dependency to `app/build.gradle.kts`
3. Sync project

## 🧪 Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

### Test Structure
- Unit tests: `app/src/test/`
- Instrumented tests: `app/src/androidTest/`

## 🐛 Debugging Tips

### Common Issues

1. **Build Errors**
   - Clean and rebuild: `Build -> Clean Project`
   - Invalidate caches: `File -> Invalidate Caches and Restart`

2. **Database Issues**
   - Check Room schema in `app/schemas/`
   - Verify entity annotations
   - Ensure proper type converters

3. **Compose Issues**
   - Use Compose preview for UI debugging
   - Check state management in ViewModels
   - Verify navigation routes

### Useful Tools

- **Layout Inspector**: For UI debugging
- **Database Inspector**: For Room database inspection
- **Logcat**: For runtime debugging
- **Compose Preview**: For UI development

## 📚 Learning Resources

### Official Documentation
- [Android Developer Guide](https://developer.android.com/guide)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Navigation Component](https://developer.android.com/guide/navigation)

### Best Practices
- Follow [Android Architecture Guidelines](https://developer.android.com/topic/architecture)
- Use [Material Design 3](https://m3.material.io/)
- Implement proper [error handling](https://developer.android.com/guide/components/activities/activity-lifecycle)
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)

## 🤝 Contributing

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex business logic
- Keep functions small and focused

### Git Workflow
1. Create feature branch from `main`
2. Make your changes
3. Test thoroughly
4. Create pull request
5. Code review and merge

### Commit Messages
Use conventional commit format:
```
feat: add user profile editing
fix: resolve login validation issue
docs: update API documentation
```

## 🆘 Getting Help

### Internal Resources
- Check existing code for patterns and examples
- Review the main `README.md` for architecture overview
- Look at similar implementations in other screens

### External Resources
- [Stack Overflow](https://stackoverflow.com/questions/tagged/android)
- [Android Developers Community](https://developer.android.com/community)
- [Kotlin Slack](https://kotlinlang.slack.com/)

## 🎯 Next Steps

1. **Explore the codebase**: Start with `MainActivity.kt` and follow the flow
2. **Run the app**: Test all features to understand user experience
3. **Read the architecture**: Understand MVVM implementation
4. **Make a small change**: Try adding a simple feature or fixing a bug
5. **Ask questions**: Don't hesitate to reach out to team members

---

**Happy coding! 🚀**

*This guide is a living document. Please update it as the project evolves.*