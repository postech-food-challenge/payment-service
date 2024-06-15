package br.com.fiap.postech.infrastructure.gateways

import br.com.fiap.postech.application.gateways.OrderServiceGateway
import br.com.fiap.postech.domain.entities.PaymentStatus
import br.com.fiap.postech.domain.exceptions.ClientException
import io.ktor.client.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.*
import java.util.*

class OrderServiceClientGateway(
    val client: HttpClient,
    val orderServiceURL: String
) : OrderServiceGateway {
    override suspend fun updatePaymentStatusOnOrderService(orderId: UUID, paymentStatus: PaymentStatus) {
        try {
            client.patch("$orderServiceURL/v1/orders/$orderId") {
                setBody(mapOf("status" to paymentStatus.name))
            }
        }
        catch (e: Exception){
            throw ClientException("Error calling Order Service", e)
        }
    }
}