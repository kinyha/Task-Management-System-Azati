package by.bratchykau.taskmanager.service.impl

import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.TaskResponse
import by.bratchykau.taskmanager.domain.dto.UpdateTaskStatusDto
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.exception.TaskNotFoundException
import by.bratchykau.taskmanager.extensions.isCompletedOnTime
import by.bratchykau.taskmanager.extensions.validateDeadline
import by.bratchykau.taskmanager.mapper.TaskMapper
import by.bratchykau.taskmanager.repository.TaskRepository
import by.bratchykau.taskmanager.repository.UserRepository
import by.bratchykau.taskmanager.service.TaskService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TaskServiceImpl(
	private val taskRepository: TaskRepository,
	private val userRepository: UserRepository,
	private val taskMapper: TaskMapper
) : TaskService {
	
	@Transactional
	override fun createTask(dto: CreateTaskDto): Task {
		val creator = userRepository.findById(dto.createdById)
			.orElseThrow { IllegalArgumentException("Creator not found") }
		
		val assignee = dto.assignedToId?.let { assigneeId ->
			userRepository.findById(assigneeId)
				.orElseThrow { IllegalArgumentException("Assignee not found") }
		}
		
		val task = taskMapper.toEntity(dto, creator, assignee)
			.validateDeadline() // Using extension function
		return taskRepository.save(task)
	}
	
	@Transactional
	override fun updateTaskStatus(dto: UpdateTaskStatusDto): Task {
		val task = getTaskById(dto.taskId)
			.also { println("Found task: ${it.id}") } // Debug
		
		task.updateStatus(dto.newStatus)
		
		return taskRepository.save(task)
			.also { println("Saved task: ${it.id}") }
	}
	
	@Transactional
	override fun assignTask(taskId: UUID, userId: UUID): Task {
		val task = getTaskById(taskId)
		val user = userRepository.findById(userId)
			.orElseThrow { IllegalArgumentException("User not found") }
		
		task.updateAssignee(user)
		return taskRepository.save(task)
	}
	
	@Transactional(readOnly = true)
	override fun findTasksByStatus(status: TaskStatus): List<TaskResponse> {
		val tasks = taskRepository.findByStatus(status)
		return taskMapper.toTaskResponseList(tasks)
	}
	
	@Transactional(readOnly = true)
	override fun getTaskById(taskId: UUID): Task {
		return taskRepository.findById(taskId)
			.orElseThrow { TaskNotFoundException(taskId) }
	}
	
	@Transactional(readOnly = true)
	override fun findOverdueTasks(): List<Task> =
		taskRepository.findAll()
			.filter { !it.isCompletedOnTime() }  // Using extension function
}