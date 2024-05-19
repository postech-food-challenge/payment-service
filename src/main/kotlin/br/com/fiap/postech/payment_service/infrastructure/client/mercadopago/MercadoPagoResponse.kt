package br.com.fiap.postech.payment_service.infrastructure.client.mercadopago

class MercadoPagoResponse(
    val qrData: String,
    val inStoreOrderId: String
)