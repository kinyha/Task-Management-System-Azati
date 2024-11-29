package by.bratchykau.taskmanager.mapper


import by.bratchykau.taskmanager.domain.dto.CreateTaskDto
import by.bratchykau.taskmanager.domain.dto.TaskDetailedInfo
import by.bratchykau.taskmanager.domain.entity.Task
import by.bratchykau.taskmanager.domain.entity.User
import org.springframework.stereotype.Component

@Component
class TaskMapper {

    fun toEntity(dto: CreateTaskDto, creator: User, assignee: User?): Task = Task(
        title = dto.title,
        description = dto.description,
        priority = dto.priority,
        assignedTo = assignee,
        createdBy = creator,
        deadline = dto.deadline
    )

    fun toDetailedInfo(task: Task) =
        TaskDetailedInfo(
            id = task.id,
            title = task.title,
            status = task.status,
            priority = task.priority,
            assignedUsername = task.assignedTo?.username,
            creatorUsername = task.createdBy.username,
            createdAt = task.createdAt,
            deadline = task.deadline
        )


    // Helper method for collections
    fun toDetailedInfoList(tasks: List<Task>): List<TaskDetailedInfo> {
        return tasks.map(::toDetailedInfo)
    }
}