package by.bratchykau.taskmanager.repository.impl

//import by.bratchykau.taskmanager.domain.entity.Task
//import by.bratchykau.taskmanager.domain.entity.User
//import by.bratchykau.taskmanager.domain.enums.TaskStatus
//import by.bratchykau.taskmanager.repository.TaskRepository
//import by.bratchykau.taskmanager.repository.UserRepository
//import org.assertj.core.api.Assertions.assertThat
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.test.context.ActiveProfiles
//import java.time.LocalDateTime
//import java.util.*
//import kotlin.test.Test
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
//class TaskRepositoryTest {
//    @Autowired
//    private lateinit var taskRepository: TaskRepository
//
//    @Autowired
//    private lateinit var userRepository: UserRepository
//
//    private val testUser = User(
//        username = "test",
//        email = "test@test.com",
//        password = "test"
//    )
//
//    private companion object {
//        const val USERNAME = "john.doe"
//        const val DEFAULT_TEST_VALUE = "qwe"
//        const val TASK_TITLE = "Overdue Task"
//    }
//
//    private fun createTestUser() = userRepository.findByUsername(USERNAME) ?: User(
//        id = UUID.randomUUID(),
//        username = DEFAULT_TEST_VALUE,
//        password = DEFAULT_TEST_VALUE,
//        email = DEFAULT_TEST_VALUE
//    )
//
//    private fun createOverdueTask(user: User) = Task(
//        title = TASK_TITLE,
//        deadline = LocalDateTime.now().minusDays(1),
//        createdBy = user,
//    )
//
//    @Test
//    fun `should find overdue tasks`() {
//        // given
//        val now = LocalDateTime.now()
//        val user = createTestUser()
//        val task = createOverdueTask(user)
//
//        taskRepository.save(task)
//
//       //when
//        val overdueTasks = taskRepository.findOverdueTasks(
//            TaskStatus.TODO,
//            now
//        )
//
//
//        // then
//        assertThat(overdueTasks)
//            .hasSize(5)
//            .first()
//            .matches { it.deadline!!.isBefore(now) }
//    }
//}