package br.com.fiap.postech.payment_service.infrastructure.gateways

import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
import br.com.fiap.postech.payment_service.domain.exceptions.PaymentNotFoundException
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepository
import java.util.UUID

class PaymentRepositoryGateway(
    private val paymentRepository: PaymentRepository
): PaymentGateway {
    override suspend fun save(payment: Payment): Payment? =
        paymentRepository.createPayment(payment)

    override suspend fun updatePaymentStatus(paymentId: Long, paymentStatus: PaymentStatus): Payment {
        return paymentRepository.updatePayment(paymentId, paymentStatus) ?:
            throw PaymentNotFoundException("Payment with id: $paymentId not found")
    }

}