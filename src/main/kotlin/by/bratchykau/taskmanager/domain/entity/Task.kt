package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.exception.InvalidTaskStatusTransitionException
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
	val id: UUID? = null, // Changed to nullable
	
	@NotBlank
	@Size(min = 3, max = 100)
	@Column(nullable = false)
	val title: String,
	
	@Size(max = 500)
	val description: String? = null,
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private var status: TaskStatus = TaskStatus.TODO, // Make it var to allow updates
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	val priority: Priority = Priority.MEDIUM,
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_user_id")
	private var assignedTo: User? = null, // Make it var to allow updates
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	val createdBy: User,
	
	val deadline: LocalDateTime? = null

) {
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	var createdAt: LocalDateTime = LocalDateTime.now()
	protected set
	
	@UpdateTimestamp
	@Column(nullable = true)
	var updatedAt: LocalDateTime? = null
		protected set
	
	// Add getters for private properties
	fun getStatus() = status
	fun getAssignedTo() = assignedTo
	
	// Add methods to update state
	fun updateStatus(newStatus: TaskStatus) {
		if (!canTransitionTo(newStatus)) {
			throw InvalidTaskStatusTransitionException(status, newStatus)
		}
		this.status = newStatus
	}
	
	fun updateAssignee(user: User?) {
		this.assignedTo = user
	}
	
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as Task
		// Compare IDs only if both are non-null
		return if (id != null && other.id != null) {
			id == other.id
		} else {
			false // New entities without IDs are never equal
		}
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
	
	val isOverdue: Boolean
		get() = deadline?.let {
			LocalDateTime.now().isAfter(it) && status != TaskStatus.DONE
		} == true
	
	/**
	 * Implements the plus operator to combine two tasks into a new task
	 * Useful for merging related tasks
	 */
	operator fun plus(other: Task): Task {
		require(this.createdBy == other.createdBy) { "Cannot merge tasks from different creators" }
		
		return Task(
			title = "${this.title} + ${other.title}",
			description = listOfNotNull(this.description, other.description)
				.joinToString("\n\n"),
			priority = maxOf(this.priority, other.priority),
			assignedTo = this.assignedTo ?: other.assignedTo,
			createdBy = this.createdBy,
			deadline = maxOf(this.deadline ?: LocalDateTime.MAX,
				other.deadline ?: LocalDateTime.MAX)
				.takeUnless { it == LocalDateTime.MAX }
		)
	}
	
	/**
	 * Implements comparison operators for tasks based on priority
	 */
	operator fun compareTo(other: Task): Int {
		return this.priority.weight.compareTo(other.priority.weight)
	}
	
	/**
	 * Implements contains operator to check if a user is involved with the task
	 */
	operator fun contains(user: User): Boolean {
		return user == createdBy || user == assignedTo
	}
}