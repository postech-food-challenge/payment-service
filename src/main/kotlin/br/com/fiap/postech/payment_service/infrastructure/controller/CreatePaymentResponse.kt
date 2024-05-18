package br.com.fiap.postech.payment_service.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
data class CreatePaymentResponse (
    val price: Int,
    val qrData: String,
    val orderId: Long
)