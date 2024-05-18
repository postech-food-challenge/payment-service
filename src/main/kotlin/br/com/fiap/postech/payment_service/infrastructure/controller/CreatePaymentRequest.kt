package br.com.fiap.postech.payment_service.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
data class CreatePaymentRequest (
    val orderId: Long,
    val totalAmount: Int,
    val description: String,
)