package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.UserRole
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class UserTest {
    @Test
    fun `should create user with default role`() {
        val user = User(username = "test_user", email = "test@example.com", passwordHash = "hashed_password")
        assertEquals(UserRole.REGULAR, user.role)
    }
}