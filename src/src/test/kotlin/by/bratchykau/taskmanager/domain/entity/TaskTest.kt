package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.exception.InvalidTaskStatusTransitionException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


class TaskTest {
    private val testUser = User(
        username = "test",
        email = "test@test.com",
        password = "test"
    )



    @Test
    fun `updateStatus() should change status from default`() {
        val task1 = Task(
            title = "Task 1",
            description = "Desc 1",
            priority = Priority.HIGH,
            createdBy = testUser
        )
        val doneStatus = TaskStatus.DONE


        task1.updateStatus(doneStatus)

        assertEquals(TaskStatus.DONE, task1.getStatus())

    }

    @Test
    fun `throw exception if status same` () {
        val task1 = Task(
            title = "Task 1",
            description = "Desc 1",
            priority = Priority.HIGH,
            createdBy = testUser
        )
        val todoStatus = TaskStatus.TODO


        assertThrows<InvalidTaskStatusTransitionException> {
            task1.updateStatus(todoStatus)
        }
    }

}