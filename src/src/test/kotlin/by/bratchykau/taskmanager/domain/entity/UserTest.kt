package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.UserRole
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class UserTest {
    @Test
    fun `should create user with default role`() {
        val user = User(username = "test_user", email = "test@example.com", password = "hashed_password")
        assertEquals(UserRole.REGULAR, user.role)
    }
}