package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.domain.entities.Payment
import br.com.fiap.postech.domain.entities.PaymentStatus

interface PaymentGateway {
    suspend fun save(payment: Payment): Payment?
    suspend fun updatePaymentStatus(paymentId: Long, paymentStatus: PaymentStatus): Payment
}