package br.com.fiap.postech.payment_service.configuration

import br.com.fiap.postech.payment_service.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.payment_service.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.payment_service.application.usecases.UpdatePaymentInteract
import br.com.fiap.postech.payment_service.infrastructure.gateways.MercadoPagoClientGateway
import br.com.fiap.postech.payment_service.infrastructure.gateways.OrderServiceClientGateway
import br.com.fiap.postech.payment_service.infrastructure.gateways.PaymentRepositoryGateway
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepository
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepositoryImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(
    orderServiceURL: String
) {
    install(Koin) {
        modules(module(orderServiceURL))
    }
}


private fun module(orderServiceURL: String) = module {

    val client = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    single<HttpClient> { client }
    single<MercadoPagoGateway> { MercadoPagoClientGateway() }
    single<PaymentRepository> { PaymentRepositoryImpl() }
    single { PaymentRepositoryGateway(get()) }
    single { CreatePaymentInteract(get(), get()) }
    single { OrderServiceClientGateway(get(), orderServiceURL) }
    single { UpdatePaymentInteract(get(), get()) }
}
