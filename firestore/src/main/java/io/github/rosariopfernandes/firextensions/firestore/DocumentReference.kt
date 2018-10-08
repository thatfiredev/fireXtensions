/*
 * MIT License
 *
 * Copyright (c) 2018 Rosário Pereira Fernandes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.rosariopfernandes.firextensions.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source

/**
 * Returns the document at that location.
 * Automatically casts to the specified data type.
 */
inline fun <reified T> DocumentReference.getDocument(
    crossinline action: (document: T?, exception: Exception?) -> Unit
) {
    getDocument<T>(Source.DEFAULT) { doc, e ->
        action(doc, e)
    }
}

/**
 * Returns the document at that location.
 * Automatically casts to the specified data type.
 * @param source Where to read the document from. Can be: Source.DEFAULT, Source.CACHE
 * or Source.SERVER
 */
inline fun <reified T> DocumentReference.getDocument(
    source: Source,
    crossinline action: (document: T?, exception: Exception?) -> Unit
) {
    get(source).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val documentSnapshot = task.result
            if (documentSnapshot.exists()) {
                if (T::class.java == DocumentSnapshot::class.java) {
                    action(task.result as T, null)
                } else {
                    action(task.result.toObject(T::class.java)!!, null)
                }
            } else {
                action(null, NullPointerException("Document not found"))
            }

        } else {
            action(null, task.exception)
        }
    }
}

/**
 * Permanently deletes the document.
 * The lambda argument is an exception if the delete fails, or
 * null if it succeeds.
 */
inline fun DocumentReference.delete(
    crossinline action: (exception: Exception?) -> Unit
) {
    delete().addOnSuccessListener { _ ->
        action(null)
    }.addOnFailureListener { e ->
        action(e)
    }
}