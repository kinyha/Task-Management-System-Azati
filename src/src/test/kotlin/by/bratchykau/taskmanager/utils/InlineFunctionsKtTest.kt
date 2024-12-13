package by.bratchykau.taskmanager.utils

import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InlineFunctionsTest {
	
	private val testUser = User(
		username = "test",
		email = "test@test.com",
		password = "test"
	)
	
	@Test
	fun `measureTimeMillis should measure execution time`() {
		val time = measureTimeMillis {
			Thread.sleep(100)
		}
		println("Execution time: $time ms")
		assertTrue(time >= 100)
	}
	
	@Test
	fun `executeIfActive should execute action only for active tasks`() {
		var executed = false
		val activeTask = Task(
			title = "Active Task",
			status = TaskStatus.IN_PROGRESS,
			createdBy = testUser
		)
		
		activeTask.executeIfActive {
			executed = true
		}
		
		assertTrue(executed)
		
		executed = false
		val blockedTask = Task(
			title = "Blocked Task",
			status = TaskStatus.BLOCKED,
			createdBy = testUser
		)
		
		blockedTask.executeIfActive {
			executed = true
		}
		
		assertTrue(!executed)
	}
	
	@Test
	fun `filterCritical should return only critical tasks`() {
		val tasks = listOf(
			Task(title = "Critical", priority = Priority.CRITICAL, createdBy = testUser),
			Task(title = "High", priority = Priority.HIGH, createdBy = testUser),
			Task(title = "Critical2", priority = Priority.CRITICAL, createdBy = testUser)
		)
		
		val criticalTasks = tasks.filterCritical()
		assertEquals(2, criticalTasks.size)
		assertTrue(criticalTasks.all { it.priority == Priority.CRITICAL })
	}
}