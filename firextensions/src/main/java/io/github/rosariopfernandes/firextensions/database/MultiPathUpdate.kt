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

import com.google.common.base.Preconditions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MultiPathUpdate(var database: FirebaseDatabase) {

    private val childUpdates = HashMap<String, Any?>()
    private var commited = false

    fun update(key: String, value: Any?) {
        Preconditions.checkState(
                !commited, "Cannot modify a MultiPathUpdate that has already been committed.")
        childUpdates[key] = value
    }

    fun push(ref: DatabaseReference, value: Any) {
        val pushKey = ref.push().key!!
        update(ref, pushKey, value)
    }

    fun setValue(ref: DatabaseReference, value: Any) {
        update(ref.getFullPath(), value)
    }

    fun update(ref: DatabaseReference, key: String, value: Any) {
        update(ref.getFullPath() + "/$key", value)
    }

    fun removeValue(ref: DatabaseReference) {
        update(ref.getFullPath(), null)
    }

    fun commit() {
        Preconditions.checkState(
                childUpdates.size != 0,
                "Either a push or update must be set.")
        database.reference.updateChildren(childUpdates)
        commited = true
    }
}