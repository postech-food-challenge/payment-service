package br.com.fiap.postech.infrastructure.controller

import br.com.fiap.postech.configuration.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreatePaymentRequest(
    @Serializable(with = UUIDSerializer::class)
    val orderId: UUID,
    val totalAmount: Int,
    val description: String,
)