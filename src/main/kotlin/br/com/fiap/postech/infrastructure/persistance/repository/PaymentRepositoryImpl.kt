package br.com.fiap.postech.infrastructure.persistance.repository

import br.com.fiap.postech.domain.entities.Payment
import br.com.fiap.postech.domain.entities.PaymentStatus
import br.com.fiap.postech.infrastructure.persistance.entity.PaymentEntity
import br.com.fiap.postech.infrastructure.persistance.repository.DatabaseSingleton.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.util.UUID

class PaymentRepositoryImpl : PaymentRepository {

    private fun resultRowToPayment(row: ResultRow) = Payment(
        paymentId = row[PaymentEntity.paymentId],
        orderId = row[PaymentEntity.orderId],
        totalAmount = row[PaymentEntity.totalAmount],
        description = row[PaymentEntity.description],
        paymentStatus = PaymentStatus.validateStatus(row[PaymentEntity.paymentStatus]),
        qrData = row[PaymentEntity.qrCode],
        createdAt = row[PaymentEntity.createdAt],
        lastModified = row[PaymentEntity.lastModified],
    )

    override suspend fun createPayment(payment: Payment): Payment? = dbQuery {
        val insertStatement = PaymentEntity.insert {
            it[orderId] = payment.orderId
            it[totalAmount] = payment.totalAmount
            it[description] = payment.description
            it[qrCode] = payment.qrData
            it[paymentStatus] = payment.paymentStatus.name
            it[createdAt] = payment.createdAt
            it[lastModified] = payment.lastModified
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPayment)
    }

    override suspend fun findPayment(paymentId: Long): Payment? = dbQuery {
        PaymentEntity
            .select { PaymentEntity.paymentId eq paymentId }
            .map(::resultRowToPayment)
            .singleOrNull()
    }

    override suspend fun updatePayment(paymentId: Long, paymentStatus: PaymentStatus): Payment? = dbQuery {
        var updated = PaymentEntity
            .update({ PaymentEntity.paymentId eq paymentId }) {
                it[PaymentEntity.paymentStatus] = paymentStatus.name
                it[lastModified] = Instant.now().toString()
            } > 0
        if (updated)
            findPayment(paymentId)
        else
            null
    }

    override suspend fun findPaymentByOrderId(orderId: UUID): Payment? = dbQuery {
        PaymentEntity
            .select{ PaymentEntity.orderId eq orderId}
            .map(::resultRowToPayment)
            .singleOrNull()
    }

}