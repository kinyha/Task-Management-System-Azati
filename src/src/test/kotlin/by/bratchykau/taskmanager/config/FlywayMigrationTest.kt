package by.bratchykau.taskmanager.config

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class FlywayMigrationTest {

	@Autowired
	private lateinit var flyway: Flyway

	@Test
	fun `migrations should apply successfully`() {
		val migrations = flyway.info().all()
		assertTrue(migrations.isNotEmpty(), "Should have migrations")
		assertTrue(migrations.all { it.state.isApplied }, "All migrations should be successful")
	}
}