package by.bratchykau.taskmanager.domain.enums

enum class Priority(val weight: Int) {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    val isUrgent get() = this in listOf(HIGH, CRITICAL)
}