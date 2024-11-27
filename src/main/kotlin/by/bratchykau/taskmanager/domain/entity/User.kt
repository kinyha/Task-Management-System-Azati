package by.bratchykau.taskmanager.domain.entity

import by.bratchykau.taskmanager.domain.enums.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.REGULAR
)