package by.bratchykau.taskmanager.config

import org.flywaydb.core.Flyway
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@TestConfiguration
class TestFlywayConfig {
	
	@Bean
	@Primary
	fun flyway(dataSource: DataSource): Flyway {
		return Flyway.configure()
			.dataSource(dataSource)
			.locations("classpath:db/migration")
			.cleanDisabled(false)
			.load()
	}
}