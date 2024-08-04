package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.OrderServiceGateway
import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.application.gateways.SqsGateway
import br.com.fiap.postech.domain.entities.PaymentStatus
import br.com.fiap.postech.infrastructure.controller.UpdatePaymentRequest
import software.amazon.awssdk.services.sqs.SqsClient

class UpdatePaymentInteract(
    private val paymentGateway: PaymentGateway,
    private val sqsGateway: SqsGateway
) {
    suspend fun updatePaymentStatusByOrderId(updatePayment: UpdatePaymentRequest) {
        val status =
            if (updatePayment.paymentValidated) PaymentStatus.PAYMENT_CONFIRMED
            else PaymentStatus.PAYMENT_DENIED

        val result = paymentGateway.updatePaymentStatus(updatePayment.paymentId, status)
        sqsGateway.updatePaymentStatusOnOrderService(result.orderId, status)
    }
}