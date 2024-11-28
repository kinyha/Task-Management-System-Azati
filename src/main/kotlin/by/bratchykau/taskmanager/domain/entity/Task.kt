package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "tasks")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    val title: String,

    @Size(max = 500)
    val description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: TaskStatus = TaskStatus.TODO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val priority: Priority = Priority.MEDIUM,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    val assignedTo: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    val createdBy: User,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,

    val deadline: LocalDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun canTransitionTo(newStatus: TaskStatus): Boolean = when {
        status == newStatus -> false
        status == TaskStatus.DONE -> false
        status == TaskStatus.BLOCKED && newStatus != TaskStatus.TODO -> false
        else -> true
    }

    fun isOverdue(): Boolean =
        deadline != null && LocalDateTime.now().isAfter(deadline) && status != TaskStatus.DONE
}