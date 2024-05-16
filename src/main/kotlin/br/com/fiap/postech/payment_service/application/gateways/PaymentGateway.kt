package br.com.fiap.postech.payment_service.application.gateways

import br.com.fiap.postech.payment_service.domain.entities.Payment

interface PaymentGateway {
    suspend fun save(payment: Payment): Payment?

    suspend fun findAll(): List<Payment>

    suspend fun findById(paymentId: Long): Payment?

    suspend fun findPaymentListByOrderId(orderId: Long): List<Payment>
    suspend fun findPaymentByOrderId(orderId: Long): Payment?
    suspend fun updatePaymentStatusByOrderId(orderId: Long, paymentValidated: Boolean): Boolean
}