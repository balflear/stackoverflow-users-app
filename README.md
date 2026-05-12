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

## Author

Kostadin Georgiev
