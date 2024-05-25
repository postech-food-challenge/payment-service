package br.com.fiap.postech.infrastructure.controller

import kotlinx.serialization.Serializable

@Serializable
class UpdatePaymentRequest(
    val paymentId: Long,
    val paymentValidated: Boolean
)