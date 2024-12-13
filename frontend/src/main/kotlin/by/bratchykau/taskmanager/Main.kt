package by.bratchykau.taskmanager

import App
import react.create
import react.dom.client.createRoot
import web.dom.document


fun main() {
    val container = document.getElementById("root") ?: error("Root container not found")
    createRoot(container).render(App.create())
}