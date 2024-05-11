package br.com.fiap.postech.payment_service.infrastructure.persistance.repository

import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity

interface PaymentRepository {
    suspend fun allPayments(): List<PaymentEntity>
    suspend fun payment(id: Long): PaymentEntity?
    suspend fun addNewPayment(totalAmount: Int, items: String): PaymentEntity?
    suspend fun deletePayment(id: Long): Boolean
}