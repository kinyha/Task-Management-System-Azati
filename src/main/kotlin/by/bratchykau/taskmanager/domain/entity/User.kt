package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    val username: String,

    @Email
    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.REGULAR,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == (other as User).id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}