package br.com.fiap.postech.domain.entities


import br.com.fiap.postech.configuration.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.security.InvalidParameterException
import java.time.Instant
import java.util.*

@Serializable
data class Payment(
    val paymentId: Long? = null,
    @Serializable(with = UUIDSerializer::class)
    val orderId: UUID,
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
