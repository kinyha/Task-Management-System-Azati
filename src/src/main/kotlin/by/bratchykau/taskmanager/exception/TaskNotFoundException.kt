package by.bratchykau.taskmanager.exception

import java.util.*

class TaskNotFoundException(taskId: UUID) :
    RuntimeException("Task not found with id: $taskId")