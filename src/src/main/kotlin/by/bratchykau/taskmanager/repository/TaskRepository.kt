package by.bratchykau.taskmanager.repository

import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*

interface TaskRepository : JpaRepository<Task, UUID>, TaskRepositoryCustom {
    fun findByAssignedTo(user: User): List<Task>
    fun findByStatus(status: TaskStatus): List<Task>

    @Query("""
        SELECT t FROM Task t 
        WHERE t.status = :status 
        AND t.deadline < :date
    """)
    fun findOverdueTasks(
        @Param("status") status: TaskStatus,
        @Param("date") date: LocalDateTime
    ): List<Task>
}