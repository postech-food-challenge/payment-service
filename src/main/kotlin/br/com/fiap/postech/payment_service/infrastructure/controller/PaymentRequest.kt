package br.com.fiap.postech.payment_service.infrastructure.controller

class PaymentRequest (
    val orderId: Long,
    val paymentValidated: Boolean
)