package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.Priority
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TaskOperatorTest {
	private val testUser = User(
		username = "test",
		email = "test@test.com",
		password = "test"
	)
	
	@Test
	fun `plus operator should merge two tasks`() {
		val task1 = Task(
			title = "Task 1",
			description = "Desc 1",
			priority = Priority.HIGH,
			createdBy = testUser
		)
		
		val task2 = Task(
			title = "Task 2",
			description = "Desc 2",
			priority = Priority.MEDIUM,
			createdBy = testUser
		)
		
		val merged = task1 + task2
		
		assertEquals("Task 1 + Task 2", merged.title)
		assertEquals("Desc 1\n\nDesc 2", merged.description)
		assertEquals(Priority.HIGH, merged.priority)
		assertEquals(testUser, merged.createdBy)
	}
	
	@Test
	fun `comparison operators should work based on priority`() {
		val highPriorityTask = Task(
			title = "High",
			priority = Priority.HIGH,
			createdBy = testUser
		)
		
		val mediumPriorityTask = Task(
			title = "Medium",
			priority = Priority.MEDIUM,
			createdBy = testUser
		)
		
		assertTrue(highPriorityTask > mediumPriorityTask)
		assertTrue(mediumPriorityTask < highPriorityTask)
	}
	
	@Test
	fun `contains operator should check user involvement`() {
		val assignee = User(
			username = "assignee",
			email = "assignee@test.com",
			password = "test"
		)
		
		val task = Task(
			title = "Test Task",
			createdBy = testUser,
			assignedTo = assignee
		)
		
		assertTrue(testUser in task)
		assertTrue(assignee in task)
		
		val otherUser = User(
			username = "other",
			email = "other@test.com",
			password = "test"
		)
		
		assertFalse(otherUser in task)
	}
}