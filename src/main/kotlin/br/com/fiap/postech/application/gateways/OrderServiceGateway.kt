package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.domain.entities.PaymentStatus
import java.util.*

interface OrderServiceGateway {
    suspend fun updatePaymentStatusOnOrderService(orderId: UUID, paymentStatus: PaymentStatus)
}