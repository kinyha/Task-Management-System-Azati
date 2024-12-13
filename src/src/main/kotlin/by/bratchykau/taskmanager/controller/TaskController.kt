package by.bratchykau.taskmanager.controller

import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.TaskResponse
import by.bratchykau.taskmanager.domain.dto.UpdateTaskStatusDto
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.mapper.TaskMapper
import by.bratchykau.taskmanager.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = ["http://localhost:8081"]) // Add CORS support
class TaskController(
	private val taskService: TaskService,
	private val taskMapper: TaskMapper,
) {
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createTask(@RequestBody dto: CreateTaskDto): TaskResponse {
		val task = taskService.createTask(dto)
		return taskMapper.toResponse(task)
	}
	
	@PutMapping("/{taskId}/status")
	fun updateTaskStatus(
		@PathVariable taskId: UUID,
		@RequestBody status: TaskStatus
	): TaskResponse {
		val task = taskService.updateTaskStatus(UpdateTaskStatusDto(taskId, status))
		return taskMapper.toResponse(task)
	}
	
	@PutMapping("/{taskId}/assign/{userId}")
	fun assignTask(
		@PathVariable taskId: UUID,
		@PathVariable userId: UUID
	): TaskResponse {
		val task = taskService.assignTask(taskId, userId)
		return taskMapper.toResponse(task)
	}
	
	@GetMapping
	fun findTasksByStatus(@RequestParam status: TaskStatus): List<TaskResponse> {
		return taskService.findTasksByStatus(status)
	}
	
	@GetMapping("/{taskId}")
	fun getTask(@PathVariable taskId: UUID): ResponseEntity<TaskResponse> {
		return try {
			val task = taskService.getTaskById(taskId)
			ResponseEntity.ok(taskMapper.toResponse(task))
		} catch (e: Exception) {
			ResponseEntity.notFound().build()
		}
	}
	
	@GetMapping("/overdueTasks")
	fun getOverdueTasks(): List<TaskResponse> {
		return taskMapper.toTaskResponseList(taskService.findOverdueTasks())
	}
}