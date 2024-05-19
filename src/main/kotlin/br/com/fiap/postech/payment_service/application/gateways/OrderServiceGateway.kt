package br.com.fiap.postech.payment_service.application.gateways

import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus
import java.util.*

interface OrderServiceGateway {
    suspend fun updatePaymentStatusOnOrderService(orderId: UUID, paymentStatus: PaymentStatus)
}