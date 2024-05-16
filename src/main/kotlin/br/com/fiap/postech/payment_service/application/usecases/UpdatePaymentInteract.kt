package br.com.fiap.postech.payment_service.application.usecases

import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.domain.entities.Payment

class UpdatePaymentInteract(
    private val gateway : PaymentGateway
) {
    suspend fun updatePaymentStatusByOrderId(updatePayment: UpdatePaymentRequest) : Boolean =
        gateway.updatePaymentStatusByOrderId(updatePayment.orderId, updatePayment.paymentValidated)

}