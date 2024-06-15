package br.com.fiap.postech.configuration

import br.com.fiap.postech.domain.exceptions.ClientException
import br.com.fiap.postech.domain.exceptions.ExceptionResponse
import br.com.fiap.postech.domain.exceptions.PaymentNotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import java.security.InvalidParameterException

fun Application.configureExceptionsResponse() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            when (throwable) {
                is PaymentNotFoundException ->
                    call.respond(
                        HttpStatusCode.NotFound,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.NotFound.value)
                    )

                is InvalidParameterException ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.BadRequest.value)
                    )

                is ClientException ->
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ExceptionResponse("${throwable.message}", HttpStatusCode.InternalServerError.value)
                    )
            }
        }
    }
}
