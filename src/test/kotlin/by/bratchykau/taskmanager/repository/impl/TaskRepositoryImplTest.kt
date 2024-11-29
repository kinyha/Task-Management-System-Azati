package by.bratchykau.taskmanager.repository.impl

import by.bratchykau.taskmanager.domain.enums.TaskStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TaskRepositoryImplTest {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    private lateinit var taskRepository: TaskRepositoryImpl

    @BeforeEach
    fun setup() {
        taskRepository = TaskRepositoryImpl(jdbcTemplate)
        setupTestData()
    }

    private fun setupTestData() {
        jdbcTemplate.execute(
            """
            INSERT INTO users (id, username, email, password, role) 
            VALUES 
            ('11111111-1111-1111-1111-111111111111', 'test-user', 'test@test.com', 'password', 'REGULAR')
        """
        )

        jdbcTemplate.execute(
            """
            INSERT INTO tasks (
                id, 
                title, 
                status, 
                priority, 
                created_by,
                created_at
            ) 
            VALUES (
                '22222222-2222-2222-2222-222222222222',
                'Test Task',
                'TODO',
                'MEDIUM',
                '11111111-1111-1111-1111-111111111111',
                CURRENT_TIMESTAMP
            )
        """
        )
    }

    @Test
    fun `should find tasks with detailed info`() {
        // when
        val result = taskDetailedInfos()

        // then
        assertThat(result).isNotEmpty()
        assertThat(result).hasSize(1)

        val task = result[0]
        assertThat(task.title).isEqualTo("Test Task")
        assertThat(task.status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `should return empty list when no tasks with given status`() {
        // given
        deleteAllTasks()

        // when
        val result = taskRepository.findTasksWithDetailedInfo(TaskStatus.TODO)

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should update multiple task statuses`() {
        // given
        val taskIds = createMultipleTasks()

        // when
        val updatedCount = taskRepository.updateTaskStatusBatch(taskIds, TaskStatus.IN_PROGRESS)

        // then
        assertThat(updatedCount).isEqualTo(taskIds.size)
    }

    @Test
    fun `should handle empty task ids list in batch update`() {
        // when
        val result = taskRepository.updateTaskStatusBatch(emptyList(), TaskStatus.DONE)

        // then
        assertThat(result).isZero()
    }

    private fun createMultipleTasks(): List<UUID> {

        val taskIds = mutableListOf<UUID>()

        for (i in 1..3) {
            val taskId = UUID.randomUUID()
            taskIds.add(taskId)

            jdbcTemplate.execute(
                """
                INSERT INTO tasks (
                    id,
                    title,
                    status,
                    priority,
                    created_by,
                    created_at
                )
                VALUES (
                    '$taskId',
                    'Test Task$i',
                    'TODO',
                    'MEDIUM',
                    '11111111-1111-1111-1111-111111111111',
                    CURRENT_TIMESTAMP
                )
            """
            )
        }

        return taskIds
    }

    private fun deleteAllTasks() {
        jdbcTemplate.execute(
            """
            DELETE FROM tasks
        """
        )
    }


    private fun taskDetailedInfos() = taskRepository.findTasksWithDetailedInfo(TaskStatus.TODO)
}