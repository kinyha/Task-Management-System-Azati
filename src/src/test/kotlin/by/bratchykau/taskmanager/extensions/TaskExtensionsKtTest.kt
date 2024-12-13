package by.bratchykau.taskmanager.extensions


import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.utils.extensions.isCompletedOnTime
import by.bratchykau.taskmanager.utils.extensions.validateDeadline
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.assertTrue

class TaskExtensionsTest {
	private val testUser = User(
		username = "test", email = "test@test.com", password = "test"
	)
	
	@Test
	fun `task completed before deadline should be completed on time`() {
		val task = Task(
			title = "Test Task",
			status = TaskStatus.DONE,
			deadline = LocalDateTime.now().plusDays(1),
			createdBy = testUser
		)
		assertTrue(task.isCompletedOnTime())
	}
	
	@Test
	fun `validateDeadline should throw for past deadline`() {
		val task = Task(
			title = "Test Task", deadline = LocalDateTime.now().minusDays(1), createdBy = testUser
		)
		
		assertThrows<IllegalArgumentException> {
			task.validateDeadline()
		}
	}
}