# fireXtensions (WIP)

fireXtensions are a set of extension functions that aim to simplify the way the Firebase SDK for Android is used with Kotlin.

### Realtime Database
#### Read Data
**Kotlin**
```
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
```
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
```
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
```
ref.observe<Todo> { todo, _ ->
            todo?.let {
                //Update the UI with received data
            }
        }
```
----
#### Read Data Once
**Kotlin**
```
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
```
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
#### Push a new object to list
**Kotlin**
```
val todo = Todo(1, "fireXtensions", "Don't forget to star this repo")
val pushKey = ref.push().key!!
ref.child(pushKey).setValue(todo)
```
**fireXtensions**
```
val todo = Todo(1, "fireXtensions", "Don't forget to star this repo")
val pushKey = ref.push(todo)
```

## Contributing
When contributing to this repository, please first discuss the change you wish to make, via issue, with the owners of this repository before making a change.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* Inspired by the [Android KTX](https://github.com/android/android-ktx)
* The function names are based on the [Official Firebase SDK for iOS](https://firebase.google.com/docs/database/ios/read-and-write).
