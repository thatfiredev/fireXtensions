[![](https://jitpack.io/v/rosariopfernandes/fireXtensions.svg)](https://jitpack.io/#rosariopfernandes/fireXtensions)

# fireXtensions

fireXtensions are a set of extension functions that aim to simplify the way the Firebase SDK for Android is used with Kotlin.

### Realtime Database
#### Read Data
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
#### Read Custom Objects
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
#### Read Data Once
**Kotlin**
```kotlin
ref.addListenerForSingleValueEvent(object: ValueEventListener {
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
ref.observeSingleEvent<DataSnapshot> { dataSnapshot, error ->
    dataSnapshot?.let {
        //Update the UI with received data
    }
    error?.let {
        //print error.message
    }
}
```
----
#### Read Lists
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
----
#### Push a new object to list
**Kotlin**
```kotlin
val todo = Todo("fireXtensions", "Don't forget to star this repo")
val pushKey = ref.push().key!!
ref.child(pushKey).setValue(todo)
```
**fireXtensions**
```kotlin
val todo = Todo("fireXtensions", "Don't forget to star this repo")
val pushKey = ref.push(todo)
```
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
Step 2 - Add the dependency to your app's build.gradle:
```gradle
    dependencies {
        implementation 'com.github.rosariopfernandes:fireXtensions:0.2'
    }
```

## Contributing
When contributing to this repository, please first discuss the change you wish to make, via issue, with the owners of this repository before making a change.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* Inspired by the [Android KTX](https://github.com/android/android-ktx)
* Some function names are based on the [Official Firebase SDK for iOS](https://firebase.google.com/docs/database/ios/read-and-write).
