package by.bratchykau.taskmanager.mapper

import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

class TaskMapperTest {
	
	private val taskMapper = TaskMapper()
	
	@Test
	fun `should map CreateTaskDto to Task entity`() {
		// given
		val creator = User(
			username = "creator",
			email = "creator@test.com",
			password = "password"
		)
		
		val dto = CreateTaskDto(
			title = "Test Task",
			description = "Description",
			priority = Priority.HIGH,
			assignedToId = null,
			deadline = LocalDateTime.now(),
			createdById = creator.id
		)
		
		// when
		val result = taskMapper.toEntity(dto, creator, null)
		
		// then
		assertEquals(dto.title, result.title)
		assertEquals(dto.description, result.description)
		assertEquals(dto.priority, result.priority)
		//assertNull(result.assignedTo)
		assertEquals(creator, result.createdBy)
	}
	
	@Test
	fun `should map Task to TaskResponse`() {
		// given
		val now = LocalDateTime.now()
		val creator = User(
			username = "creator",
			email = "creator@test.com",
			password = "password"
		)
		
		val task = Task(
			id = UUID.randomUUID(),  // Explicitly set ID for test
			title = "Test Task",
			description = "Description",
			status = TaskStatus.TODO,
			priority = Priority.HIGH,
			createdBy = creator
		)
		
		// when
		val result = taskMapper.toResponse(task)
		
		// then
		assertEquals(task.id, result.id)  // This is safe as we explicitly set the ID
		// ... rest of assertions
	}
	
	// Add new test for null ID handling
	@Test
	fun `should throw IllegalStateException when mapping Task with null ID to DetailedInfo`() {
		// given
		val creator = User(
			username = "creator",
			email = "creator@test.com",
			password = "password"
		)
		
		val task = Task(
			title = "Test Task",
			description = "Description",
			status = TaskStatus.TODO,
			priority = Priority.HIGH,
			createdBy = creator
		)
		
		// when/then
		assertThrows<IllegalStateException> {
			taskMapper.toResponse(task)
		}
	}
}
