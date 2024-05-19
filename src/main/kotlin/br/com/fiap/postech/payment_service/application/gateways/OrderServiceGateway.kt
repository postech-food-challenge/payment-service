package br.com.fiap.postech.payment_service.application.gateways

import br.com.fiap.postech.payment_service.domain.entities.PaymentStatus

interface OrderServiceGateway {
    suspend fun updatePaymentStatusOnOrderService(orderId: Long, paymentStatus: PaymentStatus)
}