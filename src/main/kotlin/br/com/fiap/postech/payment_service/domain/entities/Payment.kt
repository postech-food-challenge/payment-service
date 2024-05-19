package br.com.fiap.postech.payment_service.domain.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.security.InvalidParameterException
import java.time.Instant
import java.util.UUID

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

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}