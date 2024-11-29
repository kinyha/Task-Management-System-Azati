package by.bratchykau.taskmanager.mapper

import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
  assertNull(result.assignedTo)
  assertEquals(creator, result.createdBy)
 }

 @Test
 fun `should map Task to TaskDetailedInfo`() {
  // given
  val now = LocalDateTime.now()
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
  ).apply {
   // Initialize lateinit property for testing
   createdAt = now
  }

  // when
  val result = taskMapper.toDetailedInfo(task)

  // then
  assertEquals(task.id, result.id)
  assertEquals(task.title, result.title)
  assertEquals(task.status, result.status)
  assertEquals(task.priority, result.priority)
  assertNull(result.assignedUsername)
  assertEquals(creator.username, result.creatorUsername)
  assertEquals(now, result.createdAt)
 }
}