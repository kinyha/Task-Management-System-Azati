package by.bratchykau.taskmanager.domain.enums

enum class TaskStatus {
    TODO, IN_PROGRESS, DONE, BLOCKED;

    val isActive get() = this in listOf(TODO, IN_PROGRESS)
    val isFinal get() = this in listOf(DONE, BLOCKED)
}