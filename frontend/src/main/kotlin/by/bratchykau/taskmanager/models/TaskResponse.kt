package by.bratchykau.taskmanager.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: String,
    val title: String,
    val description: String? = null,
    val status: TaskStatus,
    val priority: Priority,
    val assignedUsername: String? = null,
    val creatorUsername: String,
    val createdAt: String,
    val deadline: String? = null
)

@Serializable
enum class TaskStatus {
    TODO, IN_PROGRESS, DONE, BLOCKED
}

@Serializable
enum class Priority {
    LOW, MEDIUM, HIGH, CRITICAL
}