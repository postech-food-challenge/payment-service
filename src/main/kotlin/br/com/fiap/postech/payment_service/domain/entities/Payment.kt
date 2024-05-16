package br.com.fiap.postech.payment_service.domain.entities

import java.time.Instant
import java.time.temporal.ChronoUnit

data class Payment(
    val paymentId: Long? = null,
    val orderId: Long,
    val totalAmount: Int,
    val description: String? = null,
    val qrCode: String,
    var paymentValidated: Boolean,
    val createdAt: Instant = Instant.now(),
    val expireAt: Instant = Instant.now().plus(2, ChronoUnit.HOURS),
    val lastModified: Instant = Instant.now(),
)
