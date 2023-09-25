# IOTD_android

Image Of The Day is an android app that show Astronomy Picture of the Day(APOD) form Nasa.
- Always loading Media from local database (room).
- Loads new content everytime app get opened
- Compatible with Android 7 and above
- App caches details offline
- Clean and Simple Material UI

## How to run?
- Go to [https://api.nasa.gov/](https://api.nasa.gov) to obtain `API KEY`
- Place generated `API KEY` in `local.properties` with property name `api_key` and sync project

## Package Structure
Root package: `com.krshashi.imageoftheday`
- `data`: Contains components of data layers 
- `di`: Contains hilt dependency modules 
- `domain`: Contains business logics 
- `network`: Contains retrofit network apis 
- `util`: Utility Classes / Kotlin extensions

## Architecture
This app uses android recommened achitecture MVVM [(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch).

## Built With
 - Kotlin : First class and official programming language for Android development
- Android Studio : The Official IDE for Android
- Gradle : Build tool for Android Studio
- Coroutines : For asynchronous programming
- Flow : A cold asynchronous data stream that sequentially emits values and completes normally or with an exception
- Android Architecture Components : Collection of libraries that help you design robust, testable, and maintainable apps
- ViewModel : Stores UI-related data that isn't destroyed on UI changes
- ViewBinding : Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views
- Room : SQLite object mapping library to store local data
- Material Components for Android : Modular and customizable Material Design UI components for Android
Dependency Injection :
  - Hilt-Dagger : Standard library to incorporate Dagger dependency injection into an Android application
  - Hilt-ViewModel : DI for injecting ViewModel
- Retrofit : A type-safe HTTP client for Android and Java. Using for network api call
- Gson Converter : A Converter which uses Gson for serialization to and from JSON
- Coil - Open source media management and image loading framework for Android written in kotlin
