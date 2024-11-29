package by.bratchykau.taskmanager.domain.dto

import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import java.time.LocalDateTime
import java.util.*

data class TaskDetailedInfo(
    val id: UUID,
    val title: String,
    val status: TaskStatus,
    val priority: Priority,
    val assignedUsername: String?,
    val creatorUsername: String,
    val createdAt: LocalDateTime,
    val deadline: LocalDateTime?
)