package by.bratchykau.taskmanager.service.impl

import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.TaskDetailedInfo
import by.bratchykau.taskmanager.domain.dto.UpdateTaskStatusDto
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.exception.InvalidTaskStatusTransitionException
import by.bratchykau.taskmanager.exception.TaskNotFoundException
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
        return taskRepository.save(task)
    }

    @Transactional
    override fun updateTaskStatus(dto: UpdateTaskStatusDto): Task {
        val task = getTaskById(dto.taskId)

        if (!task.canTransitionTo(dto.newStatus)) {
            throw InvalidTaskStatusTransitionException(task.status, dto.newStatus)
        }

        // Since Task is not a data class, we need to create a new instance
        val updatedTask = Task(
            id = task.id,
            title = task.title,
            description = task.description,
            status = dto.newStatus,  // New status
            priority = task.priority,
            assignedTo = task.assignedTo,
            createdBy = task.createdBy,
            deadline = task.deadline
        )

        return taskRepository.save(updatedTask)
    }

    @Transactional
    override fun assignTask(taskId: UUID, userId: UUID): Task {
        val task = getTaskById(taskId)
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        // Create new Task instance with updated assignee
        val updatedTask = Task(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status,
            priority = task.priority,
            assignedTo = user,  // New assignee
            createdBy = task.createdBy,
            deadline = task.deadline
        )

        return taskRepository.save(updatedTask)
    }

    @Transactional(readOnly = true)
    override fun findTasksByStatus(status: TaskStatus): List<TaskDetailedInfo> {
        val tasks = taskRepository.findByStatus(status)
        return taskMapper.toDetailedInfoList(tasks)
    }

    @Transactional(readOnly = true)
    override fun getTaskById(taskId: UUID): Task {
        return taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }
    }
}