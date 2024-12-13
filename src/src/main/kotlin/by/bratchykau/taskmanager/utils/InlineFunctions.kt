package by.bratchykau.taskmanager.utils

import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import java.time.LocalDateTime

//Inline func for measure time
inline fun measureTimeMillis(action: () -> Unit): Long {
	val startTime = System.currentTimeMillis()
	action()
	return System.currentTimeMillis() - startTime
}


inline fun Task.executeIfActive(action: (Task) -> Unit) {
	if (this.getStatus() != TaskStatus.BLOCKED && this.getStatus() != TaskStatus.DONE) {
		action(this)
	}
}

inline fun Task.executeIfActiveWithoutInline(action: (Task) -> Unit) {
	if (this.getStatus() != TaskStatus.BLOCKED && this.getStatus() != TaskStatus.DONE) {
		action(this)
	}
}

inline fun List<Task>.filterCritical(): List<Task> {
	return filter { it.priority == Priority.CRITICAL }
}

//inline func for process overdue tasks
inline fun List<Task>.processOverdueTasks(crossinline action: (Task) -> Unit) {
	filter { task ->
		task.deadline?.isBefore(LocalDateTime.now()) == true
	}.forEach(action)
}