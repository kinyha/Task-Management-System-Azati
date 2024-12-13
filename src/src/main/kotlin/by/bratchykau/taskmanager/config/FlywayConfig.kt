package by.bratchykau.taskmanager.config

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class FlywayConfig {
	
	@Bean
	@Profile("!test") // Not for test profile
	fun flywayMigrationStrategy(): FlywayMigrationStrategy {
		return FlywayMigrationStrategy { flyway ->
			// Clean database (be careful with this in production!)
			if (System.getProperty("spring.profiles.active") != "prod") {
				flyway.clean()
			}
			// Apply migrations
			flyway.migrate()
		}
	}
	
	@Bean
	@Profile("test")
	fun cleanMigrateStrategy(): FlywayMigrationStrategy {
		return FlywayMigrationStrategy { flyway ->
			flyway.clean()
			flyway.migrate()
		}
	}
}