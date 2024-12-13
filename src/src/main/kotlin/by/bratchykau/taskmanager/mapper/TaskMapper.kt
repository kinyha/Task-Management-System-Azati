package by.bratchykau.taskmanager.mapper


import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.TaskResponse
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import org.springframework.stereotype.Component

@Component
class TaskMapper {
	
	fun toEntity(dto: CreateTaskDto, creator: User, assignee: User?): Task = Task(
		title = dto.title,
		description = dto.description,
		priority = dto.priority,
		assignedTo = assignee,
		createdBy = creator,
		deadline = dto.deadline
	)
	
	
	fun toResponse(task: Task): TaskResponse {
		return TaskResponse(
			id = task.id ?: throw IllegalStateException("Task ID cannot be null"),
			title = task.title,
			description = task.description,
			status = task.getStatus(), // Use getter method
			priority = task.priority,
			assignedUsername = task.getAssignedTo()?.username, // Use getter method
			creatorUsername = task.createdBy.username,
			createdAt = task.createdAt,
			deadline = task.deadline
		)
	}
	
	// Helper method for collections
	fun toTaskResponseList(tasks: List<Task>): List<TaskResponse> {
		return tasks.map(::toResponse)
	}
	
}