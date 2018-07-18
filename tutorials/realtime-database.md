# Tutorial: Realtime Database


## Reading Data

### Listen for value events using DataSnapshot
**Kotlin**
```kotlin
postReference.addValueEventListener(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val post = dataSnapshot.getValue(String::class.java)
        //Update the UI with received data
    }

    override fun onCancelled(error: DatabaseError) {
        //print error.message
    }
})
```

**fireXtensions**
```kotlin
postReference.observe<DataSnapshot> { dataSnapshot, _ ->
    dataSnapshot?.let {
        val post = dataSnapshot.getValue(String::class.java)
        //Update the UI with received data
    }
}
```
----


### Listen for value events with custom object
**Kotlin**
```kotlin
postReference.addValueEventListener(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val post = dataSnapshot.getValue(Post::class.java)
        //Update the UI with received data
    }

    override fun onCancelled(error: DatabaseError) {
        //print error.message
    }
})
```

**fireXtensions**
```kotlin
postReference.observe<Post> { post, _ ->
    post.let {
        //Update the UI with received data
    }
}
```
----


### Read Data Once
**Kotlin**
```kotlin
postReference.addListenerForSingleValueEvent(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val post = dataSnapshot.getValue(Post::class.java)
        //Update the UI with received data
    }

    override fun onCancelled(error: DatabaseError) {
        //print error.message
    }
})
```
**fireXtensions**
```kotlin
postReference.observeSingleEvent<Post> { post, error ->
    post?.let {
        //Update the UI with received data
    }
    error?.let {
        //print error.message
    }
}
```
----


### Read Data as Lists
Those functions return ArrayLists.

**Kotlin**
```kotlin
ref.addValueEventListener(object: ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val posts = ArrayList<Post>()
        for (snapshot in dataSnapshot.children) {
            val post = snapshot.getValue(Post::class.java)
            posts.add(post!!)
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
ref.observeChildren<Post> { posts, error ->
    posts?.let {
        //Update the UI with received list
    }
    error.let {
        //print error.message
    }
}
```
----



## Writing Data

### Push a new object to a list
**Kotlin**
```kotlin
val user = User("Rosário P. Fernandes", "rosariofernandes51@gmail.com")
val pushKey = usersRef.push().key!!
usersRef.child(pushKey).setValue(user)
```

**fireXtensions**
```kotlin
val user = User("Rosário P. Fernandes", "rosariofernandes51@gmail.com")
val pushKey = usersRef.push(user)
```
----


### Multi-path updates
Most apps that use the Realtime Database require executing 2 or more operations
 simultaneously. But when doing so, there's a risk that the one of the operation might not
 succeed, leading to corrupted data.

 To avoid that, we can use multi-path updates - an atomic (all or nothing) operation. Either all
 operations succeed, or no change is made to the database.
**Kotlin**
```kotlin
val pushKey = rootRef.child("posts").push().key!!
val post = Post(userId, username, title, body)

val childUpdates = HashMap<String, Any>()
childUpdates.put("/posts/" + key, post)
childUpdates.put("/user-posts/" + userId + "/" + key, post);

rootRef.updateChildren(childUpdates);
```

**fireXtensions**
```kotlin
val post = Post(userId, username, title, body)
val database = FirebaseDatabase.getInstance()

database.runMultiPathUpdate {
    update("/posts/" + key, post)
    update("/user-posts/" + userId + "/" + key, post)
}
```

Or if you prefer to use the DatabaseReferences instead:

```kotlin
val postsRef = database.getReference("posts")
val userPostsRef = database.getReference("user-posts")

database.runMultiPathUpdate {
    update(postsRef, key, post)
    update(userPostsRef.child(userId), key, post)
}
```