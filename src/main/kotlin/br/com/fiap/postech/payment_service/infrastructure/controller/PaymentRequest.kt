package br.com.fiap.postech.payment_service.infrastructure.controller

data class PaymentRequest (
    val orderId: Long,
    val totalAmount: Int,
    val description: String,
)