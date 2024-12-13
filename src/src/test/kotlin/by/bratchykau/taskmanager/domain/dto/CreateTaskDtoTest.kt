package by.bratchykau.taskmanager.domain.dto

import by.bratchykau.taskmanager.domain.enums.Priority
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CreateTaskDtoTest {

    @Test
    fun `should create valid DTO with minimal parameters`() {
        val createdById = UUID.randomUUID()
        val dto = CreateTaskDto(
            _title = "Valid Title",
            createdById = createdById
        )

        assertEquals("Valid Title", dto.title)
        assertNull(dto.description)
        assertEquals(Priority.MEDIUM, dto.priority)
        assertNull(dto.assignedToId)
        assertNull(dto.deadline)
        assertEquals(createdById, dto.createdById)
    }

    @Test
    fun `should fail with invalid title`() {
        assertThrows<IllegalArgumentException> {
            CreateTaskDto(
                _title = "ab", // Too short
                createdById = UUID.randomUUID()
            )
        }
    }

    @Test
    fun `should fail with past deadline`() {
        assertThrows<IllegalArgumentException> {
            CreateTaskDto(
                _title = "Valid Title",
                _deadline = LocalDateTime.now().minusDays(1),
                createdById = UUID.randomUUID()
            )
        }
    }

    @Test
    fun `should copy with validated properties`() {
        val original = CreateTaskDto(
            _title = "Valid Title",
            createdById = UUID.randomUUID()
        )

        val copy = original.copy(_title = "New Valid Title")
        assertEquals("New Valid Title", copy.title)
    }
}