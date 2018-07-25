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

import com.google.firebase.database.DatabaseReference

/**
 * Create a reference to an auto-generated child location and
 * set the given data.
 */
fun DatabaseReference.push(obj: Any): String {
    val key = push().key!!
    child(key).setValue(obj)
    return key
}

inline fun DatabaseReference.push(
        obj: Any,
        crossinline action: (key: String?) -> Unit
) {
    val key = push().key
    if (key == null) {
        action(null)
    } else {
        child(key).setValue(obj).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                action(key)
            } else {
                action(null)
            }
        }
    }
}

fun DatabaseReference.getFullPath(): String {
    var path = ref.key
    var parent = ref.parent
    while (parent != null) {
        if (parent.key != null)
            path = "${parent.key}/$path"
        parent = parent.parent
    }
    return "/${path ?: ""}"
}