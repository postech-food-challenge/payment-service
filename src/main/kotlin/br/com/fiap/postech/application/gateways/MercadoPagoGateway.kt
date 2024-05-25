package br.com.fiap.postech.application.gateways

import br.com.fiap.postech.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.infrastructure.client.mercadopago.MercadoPagoResponse

interface MercadoPagoGateway {
    fun createPayment(request: MercadoPagoRequest): MercadoPagoResponse
}