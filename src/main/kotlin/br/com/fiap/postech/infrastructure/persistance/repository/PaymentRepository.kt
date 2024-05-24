package br.com.fiap.postech.payment_service.infrastructure.persistance.repository

import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
interface PaymentRepository {
    suspend fun createPayment(payment: Payment): Payment?
    suspend fun findPayment(paymentId: Long): Payment?
    suspend fun updatePayment(paymentId: Long, paymentStatus: PaymentStatus): Payment?
}