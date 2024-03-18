package com.riza.apipromo.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MyExceptionHandler {
    @ExceptionHandler
    fun springHandleNotValid(ex: BadRequestException): ResponseEntity<String> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun springHandleNotFound(ex: NotFoundException): ResponseEntity<String> {
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
