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
import br.com.fiap.postech.payment_service.infrastructure.gateways.PaymentRepositoryGateway
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init()

    val mercadoPagoGateway = MercadoPagoClientGateway()
    val paymentRepository: PaymentRepository = PaymentRepositoryImpl()
    val paymentGateway = PaymentRepositoryGateway(paymentRepository)
    val createPaymentInteract = CreatePaymentInteract(mercadoPagoGateway, paymentGateway)
    val updateGatewayInteract = UpdatePaymentInteract(paymentGateway)

    configureSerialization()
    configurePaymentController(createPaymentInteract, updateGatewayInteract)
}
