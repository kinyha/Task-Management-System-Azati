package by.bratchykau.taskmanager.domain.enums

enum class TaskStatus {
    TODO, IN_PROGRESS, DONE, BLOCKED;

    fun isActive() = this in listOf(TODO, IN_PROGRESS)
    fun isFinal() = this in listOf(DONE, BLOCKED)
}