package br.com.fiap.postech.payment_service.infrastructure.gateways

import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepository

class PaymentRepositoryGateway(
    private val paymentRepository: PaymentRepository
): PaymentGateway {
    override suspend fun save(payment: Payment): Payment? =
        paymentRepository.createPayment(payment)

    override suspend fun findAll(): List<Payment> =
        paymentRepository.allPayments()

    override suspend fun findPaymentListByOrderId(orderId: Long): List<Payment> =
        paymentRepository.findPaymentListByOrderId(orderId)

    override suspend fun findPaymentByOrderId(orderId: Long): Payment? =
        paymentRepository.findPaymentByOrderId(orderId)

    override suspend fun findById(paymentId: Long): Payment? =
        paymentRepository.findPayment(paymentId)

    override suspend fun updatePaymentStatusByOrderId(orderId: Long, paymentStatus: PaymentStatus): Boolean =
        paymentRepository.updatePaymentStatusByOrderId(orderId, paymentStatus)
}