package br.com.fiap.postech.payment_service.domain.entities

import kotlinx.serialization.Serializable
import java.security.InvalidParameterException
import java.time.Instant

@Serializable
data class Payment(
    val paymentId: Long? = null,
    val orderId: Long,
    val totalAmount: Int,
    val description: String? = null,
    val qrData: String,
    var paymentStatus: PaymentStatus = PaymentStatus.PAYMENT_PENDING,
    val createdAt: String = Instant.now().toString(),
    val lastModified: String = Instant.now().toString(),
)

@Serializable
enum class PaymentStatus {
    PAYMENT_PENDING, PAYMENT_CONFIRMED, PAYMENT_DENIED;

    companion object {
        fun validateStatus(status: String): PaymentStatus {
            return enumValues<PaymentStatus>().find { it.name == status }
                ?: throw InvalidParameterException("Invalid status: $status")
        }
    }
}