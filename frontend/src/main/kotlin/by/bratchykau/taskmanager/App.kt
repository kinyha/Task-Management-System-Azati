import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import web.cssom.ClassName
import web.cssom.px

// Define props if needed
external interface AppProps : Props

val App = FC<AppProps> {
    div {
        // Use ClassName for proper type safety
        className = ClassName("container mx-auto px-4 py-8")

        h1 {
            className = ClassName("text-3xl font-bold mb-8")
            +"Task Management System"
        }

        // Proper way to use TaskList component
        TaskList()  // Direct invocation, no .create() needed
    }
}