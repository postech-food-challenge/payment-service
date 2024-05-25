package br.com.fiap.postech.configuration

import br.com.fiap.postech.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.application.gateways.OrderServiceGateway
import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.application.usecases.UpdatePaymentInteract
import br.com.fiap.postech.infrastructure.gateways.MercadoPagoClientGateway
import br.com.fiap.postech.infrastructure.gateways.OrderServiceClientGateway
import br.com.fiap.postech.infrastructure.gateways.PaymentRepositoryGateway
import br.com.fiap.postech.infrastructure.persistance.repository.PaymentRepository
import br.com.fiap.postech.infrastructure.persistance.repository.PaymentRepositoryImpl
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
    single<PaymentGateway> { PaymentRepositoryGateway(get()) }
    single<OrderServiceGateway> { OrderServiceClientGateway(get(), orderServiceURL) }
    single { CreatePaymentInteract(get(), get()) }
    single { UpdatePaymentInteract(get(), get()) }
}
