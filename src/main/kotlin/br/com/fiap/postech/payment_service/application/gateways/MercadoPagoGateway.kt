package br.com.fiap.postech.payment_service.application.gateways

import br.com.fiap.postech.payment_service.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.payment_service.infrastructure.client.mercadopago.MercadoPagoResponse

interface MercadoPagoGateway {
    fun createPayment(request: MercadoPagoRequest): MercadoPagoResponse
}