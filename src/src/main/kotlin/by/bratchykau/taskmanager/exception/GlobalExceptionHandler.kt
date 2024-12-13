package by.bratchykau.taskmanager.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
	
	@ExceptionHandler(TaskNotFoundException::class)
	fun handleTaskNotFoundException(e: TaskNotFoundException): ResponseEntity<ErrorResponse> {
		return ResponseEntity(ErrorResponse(e.message), HttpStatus.NOT_FOUND)
	}
	
	@ExceptionHandler(InvalidTaskStatusTransitionException::class)
	fun handleInvalidStatusTransition(e: InvalidTaskStatusTransitionException): ResponseEntity<ErrorResponse> {
		return ResponseEntity(ErrorResponse(e.message), HttpStatus.BAD_REQUEST)
	}
	
	@ExceptionHandler(IllegalArgumentException::class)
	fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
		return ResponseEntity(ErrorResponse(e.message), HttpStatus.BAD_REQUEST)
	}
	
	data class ErrorResponse(
		val message: String?
	)
}