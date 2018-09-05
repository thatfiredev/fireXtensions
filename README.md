[![](https://jitpack.io/v/rosariopfernandes/fireXtensions.svg)](https://jitpack.io/#rosariopfernandes/fireXtensions)

# fireXtensions

## Introduction

fireXtensions are a set of Kotlin extension functions that aim to simplify the way you use the
 Firebase SDK for Android.

### Realtime Database
See all examples on the [Tutorial](tutorials/realtime-database.md).

**Kotlin**
```kotlin
ref.addValueEventListener(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val data = dataSnapshot.getValue(String::class.java)
        //Update the UI with received data
    }

    override fun onCancelled(error: DatabaseError) {
        //print error.message
    }
})
```

**fireXtensions**
```kotlin
ref.observe<DataSnapshot> { dataSnapshot, _ ->
    dataSnapshot?.let {
        val data = dataSnapshot.getValue(String::class.java)
        //Update the UI with received data
    }
}
```
----
**Kotlin**
```kotlin
ref.addValueEventListener(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val todo = dataSnapshot.getValue(Todo::class.java)
        //Update the UI with received data
    }

    override fun onCancelled(error: DatabaseError) {
        //print error.message
    }
})
```

**fireXtensions**
```kotlin
ref.observe<Todo> { todo, _ ->
    todo?.let {
        //Update the UI with received data
    }
}
```
----

**Kotlin**
```kotlin
ref.addValueEventListener(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val todos = ArrayList<Todo>()
        for (snapshot in dataSnapshot.children) {
            val todo = dataSnapshot.getValue(Todo::class.java)
            todos.add(todo!!)
        }
        //Update the UI with received list
    }

    override fun onCancelled(error: DatabaseError) {
        //print error.message
    }
})
```
**fireXtensions**
```kotlin
ref.observeChildren<Todo> { todos, error ->
    todos?.let {
        //Update the UI with received list
    }
    error.let {
        //print error.message
    }
}
```
More can be found on the [Tutorial](tutorials/realtime-database.md).

----

### Cloud Firestore
See all examples on the [Tutorial](tutorials/cloud-firestore.md).

**Kotlin**
```kotlin
val docRef = db.collection("cities").document("SF")
// Source can be CACHE, SERVER or DEFAULT
docRef.get(Source.CACHE).addOnCompleteListener { task ->
    if (task.isSuccessful()) {
        val document = task.result
        if (document.exists()) {
            Log.d(TAG, "DocumentSnapshot data: " + document.data)
        } else {
            Log.d(TAG, "No such document")
        }
    } else {
        Log.d(TAG, "get failed with ", task.exception)
    }
}
```

**fireXtensions**
```kotlin
// Source can be CACHE, SERVER or DEFAULT
docRef.getDocument<DocumentSnapshot>(Source.CACHE) { document, exception ->
    document?.let {
        Log.d(TAG, "DocumentSnapshot data: " + document.data)
    }
    exception?.let {
        Log.d(TAG, "get failed with ", exception)
    }
}
```
----

**Kotlin**
```kotlin
val docRef = db.collection("cities").document("BJ")
docRef.get().addOnCompleteListener { task ->
    if (task.isSuccessful()) {
        val city = task.result.toObject(City::class.java)
        //Update UI with city
    }
}
```

**fireXtensions**
```kotlin
docRef.getDocument<City>() { city, _ ->
    city?.let {
        //update UI with city
    }
}
```
More can be found on the [Tutorial](tutorials/cloud-firestore.md).


## Getting Started
Step 1 - Add the jitpack maven in your root build.gradle at the end of repositories:
```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
Step 2 - Add **one** of the dependencies to your app's build.gradle:
```gradle
    dependencies {
        // You can either import the whole library
        implementation 'com.github.rosariopfernandes:fireXtensions:0.3.4'

        // or import database extensions only
        implementation 'com.github.rosariopfernandes.fireXtensions:database:0.3.4'

        // or import firestore extensions only
        implementation 'com.github.rosariopfernandes.fireXtensions:firestore:0.3.4'
    }
```

## Contributing
When contributing to this repository, please first discuss the change you wish to make, via issue,
 with the owners of this repository before making a change.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* Inspired by [Android KTX](https://github.com/android/android-ktx)
* Some function names are based on the
 [Official Firebase SDK for iOS](https://firebase.google.com/docs/database/ios/read-and-write).
