package by.bratchykau.taskmanager.repository

import by.bratchykau.taskmanager.domain.dto.TaskDetailedInfo
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import java.util.*

interface TaskRepositoryCustom {
    fun findTasksWithDetailedInfo(status: TaskStatus): List<TaskDetailedInfo>
    fun updateTaskStatusBatch(taskIds: List<UUID>, newStatus: TaskStatus): Int
}