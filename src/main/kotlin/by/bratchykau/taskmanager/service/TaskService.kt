package by.bratchykau.taskmanager.service

import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.TaskResponse
import by.bratchykau.taskmanager.domain.dto.UpdateTaskStatusDto
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import java.util.*

interface TaskService {
    fun createTask(dto: CreateTaskDto): Task
    fun updateTaskStatus(dto: UpdateTaskStatusDto): Task
    fun assignTask(taskId: UUID, userId: UUID): Task
    fun findTasksByStatus(status: TaskStatus): List<TaskResponse>
    //fun findOverdueTasks(): List<Task>
    fun getTaskById(taskId: UUID): Task
}

