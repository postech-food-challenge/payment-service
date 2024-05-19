package br.com.fiap.postech.payment_service.infrastructure.gateways

import br.com.fiap.postech.payment_service.application.gateways.OrderServiceGateway
import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class OrderServiceClientGateway(
    val client: HttpClient,
    val orderServiceURL: String
): OrderServiceGateway {
    override suspend fun updatePaymentStatusOnOrderService(orderId: Long, paymentStatus: PaymentStatus) {
        println("AAAAAAAAAAA++++++++++AAAAAAAA++++++$paymentStatus")
        client.patch("$orderServiceURL/v1/orders/$orderId") {
            setBody(Json.encodeToString(paymentStatus))
        }
    }
}