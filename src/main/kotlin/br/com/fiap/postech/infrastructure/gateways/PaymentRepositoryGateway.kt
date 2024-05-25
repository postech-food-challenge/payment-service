package br.com.fiap.postech.infrastructure.gateways

import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.domain.entities.Payment
import br.com.fiap.postech.domain.entities.PaymentStatus
import br.com.fiap.postech.domain.exceptions.PaymentNotFoundException
import br.com.fiap.postech.infrastructure.persistance.repository.PaymentRepository

class PaymentRepositoryGateway(
    private val paymentRepository: PaymentRepository
) : PaymentGateway {
    override suspend fun save(payment: Payment): Payment? =
        paymentRepository.createPayment(payment)

    override suspend fun updatePaymentStatus(paymentId: Long, paymentStatus: PaymentStatus): Payment {
        return paymentRepository.updatePayment(paymentId, paymentStatus)
            ?: throw PaymentNotFoundException("Payment with id: $paymentId not found")
    }

}