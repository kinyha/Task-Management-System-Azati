package by.bratchykau.taskmanager.service.impl


import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.UpdateTaskStatusDto
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.exception.TaskNotFoundException
import by.bratchykau.taskmanager.mapper.TaskMapper
import by.bratchykau.taskmanager.repository.TaskRepository
import by.bratchykau.taskmanager.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TaskServiceImplTest {

    private lateinit var taskService: TaskServiceImpl
    private lateinit var taskRepository: TaskRepository
    private lateinit var userRepository: UserRepository
    private lateinit var taskMapper: TaskMapper

    // Test data - note we're using the constructor-provided ID
    private val testUser = User(
        id = UUID.randomUUID(), // Use constructor parameter
        username = "test-user",
        email = "test@test.com",
        password = "password"
    )

    @BeforeEach
    fun setup() {
        taskRepository = mock()
        userRepository = mock()
        taskMapper = mock()
        taskService = TaskServiceImpl(taskRepository, userRepository, taskMapper)
    }

    @Test
    fun `createTask should create task successfully`() {
        // Step 1: Set up test data
        val dto = CreateTaskDto(
            title = "Test Task",
            description = "Test Description",
            priority = Priority.HIGH,
            assignedToId = null,
            deadline = LocalDateTime.now().plusDays(1),
            createdById = testUser.id
        )

        // Step 2: Create the expected task that will be returned
        val expectedTask = Task(
            id = UUID.randomUUID(),
            title = dto.title,
            description = dto.description,
            priority = dto.priority,
            createdBy = testUser
        ).apply {
            createdAt = LocalDateTime.now()
        }

        // Step 3: Set up mocks in the order they'll be called
        // First: User repository lookup
        whenever(userRepository.findById(dto.createdById))
            .thenReturn(Optional.of(testUser))

        // Second: Task mapper call
        whenever(taskMapper.toEntity(any(), eq(testUser), isNull()))
            .thenReturn(expectedTask)

        // Third: Repository save - IMPORTANT: always return the task
        whenever(taskRepository.save(any<Task>()))
            .thenAnswer { expectedTask } // Use thenAnswer to ensure non-null return

        // Step 4: Execute the method
        val result = taskService.createTask(dto)

        // Step 5: Verify the result and interactions
        assertNotNull(result)
        assertEquals(expectedTask.id, result.id)
        assertEquals(expectedTask.title, result.title)

        // Verify all interactions happened in the expected order
        verify(userRepository).findById(dto.createdById)
        verify(taskMapper).toEntity(eq(dto), eq(testUser), isNull())
        verify(taskRepository).save(any())
    }

    @Test
    fun `updateTaskStatus should update task successfully`() {
        // Arrange: Set up our test data
        val taskId = UUID.randomUUID()
        val initialTask = Task(
            id = taskId,
            title = "Test Task",
            createdBy = testUser,
            status = TaskStatus.TODO  // Start with TODO status
        ).apply {
            createdAt = LocalDateTime.now()
        }

        // Create the task we expect after the update
        val expectedUpdatedTask = Task(
            id = taskId,
            title = "Test Task",
            createdBy = testUser,
            status = TaskStatus.IN_PROGRESS  // The status we're updating to
        ).apply {
            createdAt = initialTask.createdAt
        }

        // Set up our mocks
        whenever(taskRepository.findById(taskId))
            .thenReturn(Optional.of(initialTask))

        // IMPORTANT: Mock save to return the updated task
        whenever(taskRepository.save(any<Task>()))
            .thenReturn(expectedUpdatedTask)  // Always return a non-null task

        val updateDto = UpdateTaskStatusDto(
            taskId = taskId,
            newStatus = TaskStatus.IN_PROGRESS
        )

        // Act: Perform the status update
        val result = taskService.updateTaskStatus(updateDto)

        // Assert: Verify the results
        assertEquals(TaskStatus.IN_PROGRESS, result.status)
        assertEquals(taskId, result.id)

        // Verify the interactions
        verify(taskRepository).findById(taskId)
        verify(taskRepository).save(any())
    }

    @Test
    fun `updateTaskStatus should throw exception when task not found`() {
        // given
        val taskId = UUID.randomUUID()
        whenever(taskRepository.findById(taskId)) doReturn Optional.empty()

        // when & then
        assertThrows<TaskNotFoundException> {
            taskService.updateTaskStatus(
                UpdateTaskStatusDto(
                    taskId = taskId,
                    newStatus = TaskStatus.IN_PROGRESS
                )
            )
        }
    }

    @Test
    fun `assignTask should throw exception when user not found`() {
        // given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val task = Task(
            id = taskId,
            title = "Test Task",
            createdBy = testUser
        ).apply {
            createdAt = LocalDateTime.now()
        }

        whenever(taskRepository.findById(taskId)) doReturn Optional.of(task)
        whenever(userRepository.findById(userId)) doReturn Optional.empty()

        // when & then
        assertThrows<IllegalArgumentException> {
            taskService.assignTask(taskId, userId)
        }
    }
}