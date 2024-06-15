package br.com.fiap.postech

import br.com.fiap.postech.configuration.configureExceptionsResponse
import br.com.fiap.postech.configuration.configureKoin
import br.com.fiap.postech.configuration.configureSerialization
import br.com.fiap.postech.infrastructure.controller.configurePaymentController
import br.com.fiap.postech.infrastructure.persistance.repository.DatabaseSingleton
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val orderServiceURL = environment.config.property("order_service.host").getString()
    DatabaseSingleton.init(environment.config, log)
    configureKoin(orderServiceURL)
    configureSerialization()
    configurePaymentController()
    configureExceptionsResponse()
}
