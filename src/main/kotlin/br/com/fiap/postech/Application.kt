package br.com.fiap.postech

import br.com.fiap.postech.configuration.configureKoin
import br.com.fiap.postech.configuration.configureSerialization
import br.com.fiap.postech.infrastructure.controller.configurePaymentController
import br.com.fiap.postech.infrastructure.listener.PaymentStatusUpdateListener
import br.com.fiap.postech.infrastructure.persistance.repository.DatabaseSingleton
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init(environment.config, log)
    configureKoin(environment.config)
    configureSerialization()
    configurePaymentController()

    //val paymentStatusUpdateListener by inject<PaymentStatusUpdateListener>()
    //paymentStatusUpdateListener.startListening()
}
