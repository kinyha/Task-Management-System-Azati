package by.bratchykau.taskmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskManagementSystemApplication

fun main(args: Array<String>) {
	runApplication<TaskManagementSystemApplication>(*args)
}
