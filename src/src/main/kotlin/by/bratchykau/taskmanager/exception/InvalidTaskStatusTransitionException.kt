package by.bratchykau.taskmanager.exception

import by.bratchykau.taskmanager.domain.enums.TaskStatus

class InvalidTaskStatusTransitionException(
    currentStatus: TaskStatus,
    newStatus: TaskStatus
) : RuntimeException(
    "Cannot transition from $currentStatus to $newStatus"
)