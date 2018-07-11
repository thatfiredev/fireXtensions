package io.github.rosariopfernandes.firextensiossample

data class Todo(
        var title: String,
        var text: String
) {
    constructor(): this("", "")
}