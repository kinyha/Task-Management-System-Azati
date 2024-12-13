
import react.*
import react.dom.html.ReactHTML.div
import kotlinx.coroutines.launch
import by.bratchykau.taskmanager.models.TaskResponse
import by.bratchykau.taskmanager.models.TaskStatus
import kotlinx.coroutines.MainScope
import web.cssom.ClassName
import kotlin.js.Promise

external interface TaskListProps : Props {
    // Add any props if needed
}

val TaskList = FC<TaskListProps> { props ->
    // State declarations using useState hook
    var tasks by useState<List<TaskResponse>>(emptyList())
    var loading by useState(true)
    var error by useState<String?>(null)

    // Effect to load tasks
    useEffect {
        val scope = MainScope()
        scope.launch {
            try {
                val api = TaskApi()
                tasks = api.getTasks(TaskStatus.TODO)
            } catch (e: Exception) {
                error = e.message
                console.error("Failed to load tasks", e)
            } finally {
                loading = false
            }
        }
    }

    // Render loading state
    if (loading) {
        div {
            className = ClassName("p-4")
            +"Loading..."
        }
        return@FC
    }

    // Render error state
    if (error != null) {
        div {
            className = ClassName("p-4 text-red-500")
            +"Error: ${error}"
        }
        return@FC
    }

    // Render tasks
    div {
        className = ClassName("grid gap-4")
        tasks.forEach { task ->
            TaskCard {
                this.task = task
            }
        }
    }
}

// TaskCard component
external interface TaskCardProps : Props {
    var task: TaskResponse
}

val TaskCard = FC<TaskCardProps> { props ->
    div {
        className = ClassName("bg-white shadow rounded-lg p-4")
        div {
            className = ClassName("font-bold text-lg")
            +props.task.title
        }
        div {
            className = ClassName("text-gray-600 mt-2")
            +props.task.description.orEmpty()
        }
        div {
            className = ClassName("mt-4 text-sm text-gray-500")
            +"Status: ${props.task.status}"
            +"Priority: ${props.task.priority}"
        }
    }
}