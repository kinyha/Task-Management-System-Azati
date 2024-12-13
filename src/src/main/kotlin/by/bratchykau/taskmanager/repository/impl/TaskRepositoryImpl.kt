package by.bratchykau.taskmanager.repository.impl

import by.bratchykau.taskmanager.domain.dto.TaskDetailedInfo
import by.bratchykau.taskmanager.domain.enums.Priority
import by.bratchykau.taskmanager.domain.enums.TaskStatus
import by.bratchykau.taskmanager.repository.TaskRepositoryCustom
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Primary
class TaskRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate
) : TaskRepositoryCustom {

    override fun findTasksWithDetailedInfo(status: TaskStatus): List<TaskDetailedInfo> {
        val sql = """
            SELECT 
                t.id,
                t.title,
                t.status,
                t.priority,
                u.username as assigned_username,
                c.username as creator_username,
                t.created_at,
                t.deadline
            FROM tasks t
            LEFT JOIN users u ON t.assigned_user_id = u.id
            LEFT JOIN users c ON t.created_by = c.id
            WHERE t.status = ?
        """.trimIndent()

        return jdbcTemplate.query(sql, { rs, _ ->
            TaskDetailedInfo(
                id = UUID.fromString(rs.getString("id")),
                title = rs.getString("title"),
                status = TaskStatus.valueOf(rs.getString("status")),
                priority = Priority.valueOf(rs.getString("priority")),
                assignedUsername = rs.getString("assigned_username"),
                creatorUsername = rs.getString("creator_username"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                deadline = rs.getTimestamp("deadline")?.toLocalDateTime()
            )
        }, status.name)
    }

    override fun updateTaskStatusBatch(taskIds: List<UUID>, newStatus: TaskStatus): Int {
        val sql = """
            UPDATE tasks 
            SET status = ?, 
                updated_at = now() 
            WHERE id = ANY(?)
        """.trimIndent()

        return jdbcTemplate.update(sql) { ps ->
            ps.setString(1, newStatus.name)
            val array = ps.connection.createArrayOf(
                "uuid",
                taskIds.map { it.toString() }.toTypedArray()
            )
            ps.setArray(2, array)
        }
    }
}