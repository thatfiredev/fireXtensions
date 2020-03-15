# Analytics fireXtensions

## Getting Started

Step 1 - Add the jitpack maven in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2 - Add the dependency to your app's `build.gradle`:
```gradle
dependencies {
    // ...

    implementation 'com.github.rosariopfernandes.fireXtensions:analytics:0.4.0'
}
```

## Features

### Get the default FirebaseAnalytics instance

**Kotlin**
```kotlin
val analytics = FirebaseAnalytics.getInstance(context)
```

**Kotlin + KTX**
```kotlin
val analytics = Firebase.analytics(context)
```
