package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.domain.entities.Payment
import br.com.fiap.postech.domain.entities.PaymentStatus
import java.util.UUID

interface PaymentGateway {
    suspend fun save(payment: Payment): Payment?
    suspend fun updatePaymentStatus(paymentId: Long, paymentStatus: PaymentStatus): Payment
    suspend fun getPaymentByOrderId( orderId: UUID): Payment?
}