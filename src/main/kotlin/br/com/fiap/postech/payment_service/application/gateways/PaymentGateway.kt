package br.com.fiap.postech.payment_service.application.gateways

import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
import java.util.UUID

interface PaymentGateway {
    suspend fun save(payment: Payment): Payment?
    suspend fun updatePaymentStatus(paymentId: Long, paymentStatus: PaymentStatus): Payment
}