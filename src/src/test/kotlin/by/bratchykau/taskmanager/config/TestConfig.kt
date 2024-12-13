package by.bratchykau.taskmanager.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@TestConfiguration
class TestConfig {
    @Bean
    @Primary
    fun testDataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/task_manager_test")
            .username("postgres")
            .password("postgres")
            .driverClassName("org.postgresql.Driver")
            .build()
    }
    
    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}