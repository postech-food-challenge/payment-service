package br.com.fiap.postech.payment_service.infrastructure.controller

class PaymentResponse (
    val orderId: Long? = null,
    val paymentValidated: Boolean?
)