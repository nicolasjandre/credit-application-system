package jandre.creditapplicationsystem.exception.handler

import jakarta.validation.ConstraintViolationException
import jandre.creditapplicationsystem.dto.error.ErrorResponseDto
import jandre.creditapplicationsystem.exception.BadRequestException
import jandre.creditapplicationsystem.exception.NotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(notFoundException: NotFoundException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponseDto(notFoundException.message!!, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value())
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun notFoundException(badRequestException: BadRequestException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponseDto(badRequestException.message!!, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value())
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDto> {
        val errorMessages = ex.bindingResult.allErrors.map { error ->
            (error as FieldError).defaultMessage
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto(errorMessages.toString(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()))
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponseDto> {
        val errorMessage: String = when {
            ex.message?.contains("uc_customer_ctm_tx_cpf") == true ->
                "User with this CPF already exists"
            ex.message?.contains("uc_customer_ctm_tx_email") == true ->
                "User with this email already exists"
            else -> ex.message ?: "Erro de integridade de dados"
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorResponseDto(errorMessage, HttpStatus.CONFLICT, HttpStatus.CONFLICT.value()))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponseDto> {
        val errorMessage: String = ex.localizedMessage ?: "Erro de validação de constraint"

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponseDto(errorMessage, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()))
    }
}