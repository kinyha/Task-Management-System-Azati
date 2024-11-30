package by.bratchykau.taskmanager.extensions

import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

// Business logic extensions
fun Task.isCompletedOnTime(): Boolean = when {
	getStatus() != TaskStatus.DONE -> false
	deadline == null -> true
	else -> {
		val completionTime = updatedAt ?: LocalDateTime.now()
		!completionTime.isAfter(deadline)
	}
}

fun Task.daysUntilDeadline(): Long? = deadline?.let {
	ChronoUnit.DAYS.between(LocalDateTime.now(), it)
}

// Validation extensions
fun Task.validateDeadline(): Task = apply {
	deadline?.let {
		require(it.isAfter(LocalDateTime.now())) {
			"Deadline must be in the future"
		}
	}
}

// Status checking extensions
fun Task.isBlocked(): Boolean = getStatus() == TaskStatus.BLOCKED
fun Task.isActive(): Boolean = getStatus().isActive