package by.bratchykau.taskmanager.domain.dto

import by.bratchykau.taskmanager.domain.enums.Priority
import java.time.LocalDateTime
import java.util.*

data class CreateTaskDto(
    val title: String,
    val description: String?,
    val priority: Priority = Priority.MEDIUM,
    val assignedToId: UUID?,
    val deadline: LocalDateTime?,
    val createdById: UUID
) {
    // Validation extension function
    fun validate() = apply {
        require(title.length in 3..100) { "Title must be between 3 and 100 characters" }
        deadline?.let {
            require(it.isAfter(LocalDateTime.now())) { "Deadline must be in the future" }
        }
    }
}