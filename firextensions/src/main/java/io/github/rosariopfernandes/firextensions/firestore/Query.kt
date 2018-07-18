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

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source

inline fun <reified T> Query.getDocuments(
    crossinline action: (documents: ArrayList<T>?, exception: Exception?) -> Unit
) {
    getDocuments<T>(Source.DEFAULT) { docs, e ->
        action(docs, e)
    }
}

inline fun <reified T> Query.getDocuments(
    source: Source,
    crossinline action: (documents: ArrayList<T>?, exception: Exception?) -> Unit
) {
    get(source).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val docs = ArrayList<T>()
            for (snapshot in task.result) {
                if (T::class.java == DocumentSnapshot::class.java) {
                    docs.add(snapshot as T)
                } else {
                    docs.add(snapshot.toObject(T::class.java))
                }
            }
            action(docs, null)
        } else {
            action(null, task.exception)
        }
    }
}