import by.bratchykau.taskmanager.models.TaskResponse
import by.bratchykau.taskmanager.models.TaskStatus
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class TaskApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun getTasks(status: TaskStatus): List<TaskResponse> {
        return client.get("http://localhost:8080/api/v1/tasks") {
            parameter("status", status)
        }.body()
    }

    suspend fun getTask(id: String): TaskResponse {
        return client.get("http://localhost:8080/api/v1/tasks/$id").body()
    }

    suspend fun updateTaskStatus(taskId: String, status: TaskStatus) {
        client.put("http://localhost:8080/api/v1/tasks/$taskId/status") {
            setBody(status)
        }
    }
}