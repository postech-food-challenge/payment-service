package br.com.fiap.postech.payment_service.domain.entities

import java.security.InvalidParameterException
import java.time.Instant
import java.time.temporal.ChronoUnit

data class Payment(
    val paymentId: Long? = null,
    val orderId: Long,
    val totalAmount: Int,
    val description: String? = null,
    val qrCode: String,
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val createdAt: Instant = Instant.now(),
    val expireAt: Instant = Instant.now().plus(2, ChronoUnit.HOURS),
    val lastModified: Instant = Instant.now(),
)

enum class PaymentStatus {
    PENDING, CONCLUDED, CANCELED, EXPIRED;

    companion object {
        fun validateStatus(status: String): PaymentStatus {
            return enumValues<PaymentStatus>().find { it.name == status }
                ?: throw InvalidParameterException("Invalid status: $status")
        }
    }
}