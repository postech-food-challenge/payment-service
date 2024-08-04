package br.com.fiap.postech.configuration

import br.com.fiap.postech.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.application.gateways.OrderServiceGateway
import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.application.gateways.SqsGateway
import br.com.fiap.postech.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.application.usecases.UpdatePaymentInteract
import br.com.fiap.postech.infrastructure.gateways.MercadoPagoClientGateway
import br.com.fiap.postech.infrastructure.gateways.OrderServiceClientGateway
import br.com.fiap.postech.infrastructure.gateways.PaymentRepositoryGateway
import br.com.fiap.postech.infrastructure.gateways.SqsClientGateway
import br.com.fiap.postech.infrastructure.listener.PaymentStatusUpdateListener
import br.com.fiap.postech.infrastructure.persistance.repository.PaymentRepository
import br.com.fiap.postech.infrastructure.persistance.repository.PaymentRepositoryImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import java.net.URI
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

fun Application.configureKoin(
    config: ApplicationConfig
) {
    install(Koin) {
        modules(module(config))
    }
}


private fun module(config: ApplicationConfig) = module {

    val orderServiceURL = config.property("order_service.host").getString()
    val paymentUpdateQueue = config.property("aws.queue.payment_update").getString()
    val awsConfiguration = AwsConfiguration(config)

    single<SqsClient> {
        val sqsClient = SqsClient.builder()
            .region(Region.of(awsConfiguration.region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(awsConfiguration.accessKey, awsConfiguration.secretAccessKey)
                )
            )
        if (awsConfiguration.account == "000000000000")
            sqsClient.endpointOverride(URI(awsConfiguration.baseUrl))

        sqsClient.build()
    }

    single<HttpClient> {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }

    single <SqsGateway> { SqsClientGateway(get(), awsConfiguration, paymentUpdateQueue) }
    single { PaymentStatusUpdateListener(get(), awsConfiguration, paymentUpdateQueue) }
    single<MercadoPagoGateway> { MercadoPagoClientGateway() }
    single<PaymentRepository> { PaymentRepositoryImpl() }
    single<PaymentGateway> { PaymentRepositoryGateway(get()) }
    single<OrderServiceGateway> { OrderServiceClientGateway(get(), orderServiceURL) }
    single { CreatePaymentInteract(get(), get()) }
    single { UpdatePaymentInteract(get(), get(), get()) }
}
