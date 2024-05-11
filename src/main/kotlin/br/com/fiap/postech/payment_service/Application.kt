package br.com.fiap.postech.payment_service

import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.DatabaseSingleton
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepository
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepositoryImpl
import br.com.fiap.postech.payment_service.plugins.configureSerialization
import br.com.fiap.postech.payment_service.infrastructure.controller.configurePaymentController
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init()

    val repository: PaymentRepository = PaymentRepositoryImpl().apply {
        runBlocking {
            if(allPayments().isEmpty()) {
                addNewPayment(12, "ableble")
            }
        }
    }
    configureSerialization()
    configurePaymentController(repository)
}
