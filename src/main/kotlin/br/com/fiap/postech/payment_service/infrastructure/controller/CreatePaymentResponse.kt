package br.com.fiap.postech.payment_service.infrastructure.controller

import br.com.fiap.postech.payment_service.domain.entities.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreatePaymentResponse (
    val price: Int,
    val qrData: String,
    @Serializable(with = UUIDSerializer::class)
    val orderId: UUID
)