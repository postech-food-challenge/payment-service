package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.OrderServiceGateway
import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.domain.entities.PaymentStatus
import br.com.fiap.postech.infrastructure.controller.UpdatePaymentRequest

class UpdatePaymentInteract(
    private val paymentGateway: PaymentGateway,
    private val orderServiceGateway: OrderServiceGateway
) {
    suspend fun updatePaymentStatusByOrderId(updatePayment: UpdatePaymentRequest) {
        val status =
            if (updatePayment.paymentValidated) PaymentStatus.PAYMENT_CONFIRMED
            else PaymentStatus.PAYMENT_DENIED

        val result = paymentGateway.updatePaymentStatus(updatePayment.paymentId, status)
        orderServiceGateway.updatePaymentStatusOnOrderService(result.orderId, status)

    }
}