package by.bratchykau.taskmanager.domain

import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.TaskList
import by.bratchykau.taskmanager.domain.entity.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TaskListTest {
	private val testUser = User(
		username = "test",
		email = "test@test.com",
		password = "test"
	)
	
	@Test
	fun `plusAssign operator should add task to list`() {
		val taskList = TaskList()
		val task = Task(
			title = "Test Task",
			createdBy = testUser
		)
		
		taskList += task
		
		assertEquals(1, taskList.size())
		assertTrue(task in taskList)
	}
	
	@Test
	fun `get operator should return task at index`() {
		val taskList = TaskList()
		val task = Task(
			title = "Test Task",
			createdBy = testUser
		)
		
		taskList += task
		
		assertEquals(task, taskList[0])
	}
	
	@Test
	fun `set operator should update task at index`() {
		val taskList = TaskList()
		val task1 = Task(
			title = "Task 1",
			createdBy = testUser
		)
		val task2 = Task(
			title = "Task 2",
			createdBy = testUser
		)
		
		taskList += task1
		taskList[0] = task2
		
		assertEquals(task2, taskList[0])
	}
}