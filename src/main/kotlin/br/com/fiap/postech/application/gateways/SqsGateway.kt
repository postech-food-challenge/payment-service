package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.infrastructure.gateways.dto.PaymentStatusUpdateDTO

interface SqsGateway {
    suspend fun updatePaymentStatusOnOrderService(dto: PaymentStatusUpdateDTO)
}