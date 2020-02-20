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

import com.google.android.gms.common.internal.Preconditions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Allows execution of multiple operations atomically.
 */
@Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
class MultiPathUpdate(var database: FirebaseDatabase) {

    private val childUpdates = HashMap<String, Any?>()
    private var commited = false

    /**
     * Replaces the object at the specified path with the object
     * specified as value. If the object already exists, it will be updated.
     * If the object does not exist, it will be created.
     * @param path the path to the reference to be updated.
     * @param value the new value for that reference.
     */
    @Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
    fun update(path: String, value: Any?) {
        Preconditions.checkState(
                !commited, "Cannot modify a MultiPathUpdate that has already been committed.")
        childUpdates[path] = value
    }

    /**
     * Create a reference to an auto-generated child location and
     * set the given data.
     * @param ref
     * @param data the data to be added.
     */
    @Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
    fun push(ref: DatabaseReference, data: Any) {
        val pushKey = ref.push().key!!
        update(ref, pushKey, data)
    }

    /**
     * Creates an object at the specified path with the value
     * specified. If the object already exists, it will be overwritten.
     */
    @Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
    fun setValue(ref: DatabaseReference, value: Any) {
        update(ref.getFullPath(), value)
    }

    /**
     * Updates the object that has the given key at the given reference.
     * @param ref the DatabaseReference where we can find the object to be updated.
     * @param key the key of the object that should be updated
     * @param value the new value for that object.
     */
    @Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
    fun update(ref: DatabaseReference, key: String, value: Any) {
        update(ref.getFullPath() + "/$key", value)
    }

    /**
     * Deletes any object (and it's child) found at that DatabaseReference.
     * If the reference contains a single object, the reference will also be deleted.
     */
    @Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
    fun removeValue(ref: DatabaseReference) {
        ref.removeValue()
    }

    /**
     * Executes the atomic operation. This should be the last method called.
     */
    @Deprecated("fireXtensions database have been deprecated in favor of Firebase KTX")
    fun commit() {
        Preconditions.checkState(
                childUpdates.size != 0,
                "Either a push or update must be set.")
        database.reference.updateChildren(childUpdates)
        commited = true
    }
}