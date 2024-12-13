package by.bratchykau.taskmanager.domain.dto

import by.bratchykau.taskmanager.domain.enums.TaskStatus
import java.util.*

data class UpdateTaskStatusDto(
    val taskId: UUID,
    val newStatus: TaskStatus
)