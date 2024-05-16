package br.com.fiap.postech.payment_service.application.usecases

import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway

class FindPaymentByOrderIdInteract(
    private val gateway : PaymentGateway
) {
    suspend fun findPaymentListByOrderId(orderId: Long) =
        gateway.findPaymentListByOrderId(orderId)

    suspend fun findPaymentByOrderId(orderId: Long) =
        gateway.findPaymentListByOrderId(orderId)
}