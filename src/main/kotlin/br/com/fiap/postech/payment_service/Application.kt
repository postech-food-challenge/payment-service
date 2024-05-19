package br.com.fiap.postech.payment_service

import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.payment_service.application.usecases.UpdatePaymentInteract
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.DatabaseSingleton
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepository
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepositoryImpl
import br.com.fiap.postech.payment_service.plugins.configureSerialization
import br.com.fiap.postech.payment_service.infrastructure.controller.configurePaymentController
import br.com.fiap.postech.payment_service.infrastructure.gateways.MercadoPagoClientGateway
import br.com.fiap.postech.payment_service.infrastructure.gateways.OrderServiceClientGateway
import br.com.fiap.postech.payment_service.infrastructure.gateways.PaymentRepositoryGateway
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val client = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.INFO
        }
    }
    val orderServiceURL = environment.config.property("order_service.host").getString()
    DatabaseSingleton.init(environment.config)

    val mercadoPagoGateway = MercadoPagoClientGateway()
    val paymentRepository = PaymentRepositoryImpl()
    val paymentGateway = PaymentRepositoryGateway(paymentRepository)
    val createPaymentInteract = CreatePaymentInteract(mercadoPagoGateway, paymentGateway)
    val orderServiceGateway = OrderServiceClientGateway(client, orderServiceURL)
    val updateGatewayInteract = UpdatePaymentInteract(paymentGateway, orderServiceGateway)

    configureSerialization()
    configurePaymentController(createPaymentInteract, updateGatewayInteract)
}
