package br.com.fiap.postech.payment_service.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
class UpdatePaymentRequest(
    val orderId: Long,
    val paymentValidated: Boolean
)