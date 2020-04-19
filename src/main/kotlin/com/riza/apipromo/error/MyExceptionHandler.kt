package com.riza.apipromo.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class MyExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BadRequestException::class)
    @Throws(IOException::class)
    fun springHandleNotValid(response: HttpServletResponse) {
        response.sendError(HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(NotFoundException::class)
    @Throws(IOException::class)
    fun springHandleNotFound(response: HttpServletResponse) {
        response.sendError(HttpStatus.NOT_FOUND.value())
    }

}
