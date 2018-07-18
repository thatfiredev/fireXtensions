# Tutorial: Cloud Firestore


## Get Documents

### Get a single Document
**Kotlin**
```kotlin
val docRef = db.collection("cities").document("SF")
docRef.get().addOnCompleteListener { task ->
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
docRef.getDocument<DocumentSnapshot>() { document, exception ->
    document?.let {
        Log.d(TAG, "DocumentSnapshot data: " + document.data)
    }
    exception?.let {
        Log.d(TAG, "get failed with ", exception)
    }
}
```
----


### Source Options
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


### Custom Objects
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
----


### Get multiple Documents
Those functions return ArrayLists.

**Kotlin**
```kotlin
db.collection("cities")
        .whereEqualTo("capital", true)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                val cities = ArrayList<City>()
                for (document in task.result) {
                    cities.add(document.data)
                }
                //Use the cities ArrayList
            }
        }
```

**fireXtensions**
```kotlin
db.collection("cities")
    .whereEqualTo("capital", true)
    .getDocuments<City>() { cities, _ ->
        cities?.let {
            //Use the cities ArrayList
        }
    }
```
----


## Write Data

### Batched writes
If you do not need to read any documents in your operation set, you can execute multiple
 write operations as a single batch that contains any combination of `set()`, `update()`,
 or `delete()` operations. A batch of writes completes atomically and can write to multiple
 documents.
**Kotlin**
```kotlin
val db = FirebaseFirestore.getInstance()
val batch = db.batch()

// Set the value of 'NYC'
val nycRef = db.collection("cities").document("NYC")
batch.set(nycRef, City("New York"))

// Update the population of 'SF'
val sfRef = db.collection("cities").document("SF")
batch.update(sfRef, "population", 1000000L)

// Delete the city 'LA'
val laRef = db.collection("cities").document("LA")
batch.delete(laRef)

// Commit the batch
batch.commit()
```

**fireXtensions**
```kotlin
val nycRef = db.collection("cities").document("NYC")
val sfRef = db.collection("cities").document("SF")
val laRef = db.collection("cities").document("LA")
db.writeBatch {
    // Set the value of 'NYC'
    set(nycRef, City("New York"))

    // Update the population of 'SF'
    update(sfRef, "population", 1000000L)

    // Delete the city 'LA'
    delete(laRef)
}
```