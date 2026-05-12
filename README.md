# StackOverflow Users App

This is a simple native Android application that integrates with the Stack Exchange REST API and
fetches the top 20 Stack Overflow users by reputation.

The app is built as a small but structured example project. It follows modern Android practices and
frameworks to keep the code maintainable, readable, and scalable.

## Architecture

The project follows:

- Clean Architecture
- MVVM
- MVI-style state handling(unidirectional data flow)
- Dependency Injection
- Repository pattern
- Use Cases

The codebase is separated into `presentation`, `domain`, and `data` layers

## What the app does

- Requests the top 20 Stack Overflow users from the REST API
- Displays the users in a native Android UI built with Jetpack Compose
- Allows following and unfollowing users locally
- Persists followed users using a local Room database

## Main libraries and dependencies

Some of the main libraries used in the project are:

- Kotlin
- Jetpack Compose
- AndroidX Lifecycle
- Hilt for dependency injection
- Retrofit for REST API integration
- Gson Converter for JSON parsing
- OkHttp Logging Interceptor for network logging
- Room for local persistence
- Coil for image loading
- Kotlin Coroutines for asynchronous work
- Kotlin Flow for state and stream handling
- JUnit and MockK for testing

## How to run

- Clone the repo
- Open the project in Android Studio
- Sync Gradle
- Run the `app` module on an emulator or physical Android device
- Internet connection is required for loading the users list
- Minimum supported Android version: 7.0 (SDK 24)

## Technical decisions

- I used Clean Architecture to keep the presentation, domain, and data layers separated.
- I used MVVM to keep the UI logic separate from the data and business logic.
- I used MVI-style state handling to keep state updates predictable and easier to follow.
- I used Kotlin Flow to observe and update screen state in a reactive way that works well with
  Jetpack Compose.
- I used Room to persist the users follow state between app sessions.
- I used a repository to combine the remote API data with the local follow state.
- I used Hilt for dependency injection.

## Author

Kostadin Georgiev
