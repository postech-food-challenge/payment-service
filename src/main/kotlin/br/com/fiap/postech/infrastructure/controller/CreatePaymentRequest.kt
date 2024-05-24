package br.com.fiap.postech.payment_service.infrastructure.controller

import br.com.fiap.postech.payment_service.configuration.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreatePaymentRequest (
    @Serializable(with = UUIDSerializer::class)
    val orderId: UUID,
    val totalAmount: Int,
    val description: String,
)