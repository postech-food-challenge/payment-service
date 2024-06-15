package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.domain.entities.Payment
import java.util.UUID

class GetPaymentInteract(
    private val paymentGateway: PaymentGateway,
) {
    suspend fun getPaymentByOrderId(orderId : UUID): Payment? =
        paymentGateway.getPaymentByOrderId(orderId)
}