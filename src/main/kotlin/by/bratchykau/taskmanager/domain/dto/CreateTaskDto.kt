package by.bratchykau.taskmanager.domain.dto

import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.utils.delegates.futureDate
import by.bratchykau.taskmanager.utils.delegates.stringLength
import java.time.LocalDateTime
import java.util.*

data class CreateTaskDto(
    private var _title: String,
    val description: String? = null,
    val priority: Priority = Priority.MEDIUM,
    val assignedToId: UUID? = null,
    private var _deadline: LocalDateTime? = null,
    val createdById: UUID
) {
    var title: String by stringLength(3, 100, "Title")
        private set

    var deadline: LocalDateTime? by futureDate({ it.isAfter(LocalDateTime.now()) }, "Deadline")
        private set

    init {
        title = _title       // Validation happens here through delegate
        deadline = _deadline // Validation happens here through delegate
    }
}


