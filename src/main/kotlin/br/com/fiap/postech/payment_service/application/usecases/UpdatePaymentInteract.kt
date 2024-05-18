package br.com.fiap.postech.payment_service.application.usecases

import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
import br.com.fiap.postech.payment_service.infrastructure.controller.UpdatePaymentRequest

class UpdatePaymentInteract(
    private val gateway : PaymentGateway
) {
    suspend fun updatePaymentStatusByOrderId(updatePayment: UpdatePaymentRequest) {
        if (updatePayment.paymentValidated)
            gateway.updatePaymentStatusByOrderId(updatePayment.orderId, PaymentStatus.CONCLUDED)
        else
            gateway.updatePaymentStatusByOrderId(updatePayment.orderId, PaymentStatus.CANCELED)
    }
}