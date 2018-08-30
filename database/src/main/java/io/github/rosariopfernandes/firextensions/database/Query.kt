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

package io.github.rosariopfernandes.firextensions.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

/**
 * This method will be called with a snapshot of the data at this location
 * casted to the given type.
 */
inline fun <reified T> DatabaseReference.observe(
    crossinline action: (data: T?, error: DatabaseError?) -> Unit
) {
    addValueEventListener(object: ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if(T::class.java == DataSnapshot::class.java) {
                action(dataSnapshot as T, null)
            } else {
                action(dataSnapshot.getValue(T::class.java), null)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            action(null, error)
        }
    })
}

/**
 * This method will be called with a snapshot of a single change to
 * the data at this location, casted to the given type.
 */
inline fun <reified T> DatabaseReference.observeSingleEvent(
    crossinline action: (data: T?, error: DatabaseError?) -> Unit
) {
    addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if(T::class.java == DataSnapshot::class.java) {
                action(dataSnapshot as T, null)
            } else {
                action(dataSnapshot.getValue(T::class.java), null)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            action(null, error)
        }
    })
}

/**
 * Add a listener for child events occurring at this location.
 * Casts each child to the specified type.
 */
inline fun <reified T> DatabaseReference.observeChildren(
    crossinline action: (data: ArrayList<T>?, error: DatabaseError?) -> Unit
) {
    addValueEventListener(object: ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val data = ArrayList<T>()
            for (snapshot in dataSnapshot.children) {
                if (T::class.java == DataSnapshot::class.java) {
                    data.add(snapshot as T)
                } else {
                    data.add(snapshot.getValue(T::class.java)!!)
                }
            }
            action(data, null)
        }

        override fun onCancelled(error: DatabaseError) {
            action(null, error)
        }
    })
}

/**
 * Add a listener for a single child event occurring at this location.
 * Casts each child to the specified type.
 */
inline fun <reified T> DatabaseReference.observeSingleChildrenEvent(
    crossinline action: (data: ArrayList<T>?, error: DatabaseError?) -> Unit
) {
    addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val data = ArrayList<T>()
            for (snapshot in dataSnapshot.children) {
                if (T::class.java == DataSnapshot::class.java) {
                    data.add(snapshot as T)
                } else {
                    data.add(snapshot.getValue(T::class.java)!!)
                }
            }
            action(data, null)
        }

        override fun onCancelled(error: DatabaseError) {
            action(null, error)
        }
    })
}