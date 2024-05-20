package br.com.fiap.postech.payment_service

import br.com.fiap.postech.payment_service.configuration.configureKoin
import br.com.fiap.postech.payment_service.configuration.configureSerialization
import br.com.fiap.postech.payment_service.infrastructure.controller.configurePaymentController
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.DatabaseSingleton
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val orderServiceURL = environment.config.property("order_service.host").getString()
    DatabaseSingleton.init(environment.config, log)
    configureKoin(orderServiceURL)
    configureSerialization()
    configurePaymentController()
}
