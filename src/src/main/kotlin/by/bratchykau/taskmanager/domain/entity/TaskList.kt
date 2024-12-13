package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.TaskStatus

/**
 * Represents a collection of tasks with custom operators
 */
class TaskList(private val tasks: MutableList<Task> = mutableListOf()) {
	
	operator fun plusAssign(task: Task) {
		tasks.add(task)
	}
	
	operator fun get(index: Int): Task = tasks[index]
	
	operator fun set(index: Int, task: Task) {
		tasks[index] = task
	}
	
	operator fun contains(task: Task): Boolean = tasks.contains(task)
	
	fun filterByStatus(status: TaskStatus): TaskList =
		TaskList(tasks.filter { it.getStatus() == status }.toMutableList())
	
	fun filterByUser(user: User): TaskList =
		TaskList(tasks.filter { user in it }.toMutableList())
	
	fun size(): Int = tasks.size
	
	fun toList(): List<Task> = tasks.toList()
}