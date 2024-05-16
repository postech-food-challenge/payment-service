package br.com.fiap.postech.payment_service.infrastructure.persistance.repository

import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.DatabaseSingleton.dbQuery
import org.jetbrains.exposed.sql.*

class PaymentRepositoryImpl: PaymentRepository{

    private fun resultRowToPayment(row: ResultRow) = Payment(
        paymentId = row[PaymentEntity.paymentId],
        orderId = row[PaymentEntity.orderId],
        totalAmount = row[PaymentEntity.totalAmount],
        description = row[PaymentEntity.description],
        paymentValidated = row[PaymentEntity.paymentValidated],
        qrCode = row[PaymentEntity.qrCode],
        createdAt = row[PaymentEntity.createdAt],
        lastModified = row[PaymentEntity.lastModified],
        expireAt = row[PaymentEntity.expireAt],
    )
    override suspend fun allPayments(): List<Payment> = dbQuery {
        PaymentEntity.selectAll().map(::resultRowToPayment)
    }

    override suspend fun createPayment(payment: Payment): Payment? = dbQuery {
        val insertStatement = PaymentEntity.insert {
            it[orderId] = payment.orderId
            it[totalAmount] = payment.totalAmount
            it[description] = payment.description
            it[qrCode] = payment.qrCode
            it[paymentValidated] = payment.paymentValidated
            it[createdAt] = payment.createdAt
            it[expireAt] = payment.expireAt
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

    override suspend fun findPaymentListByOrderId(orderId: Long): List<Payment> = dbQuery {
        PaymentEntity
            .select { PaymentEntity.orderId eq orderId }
            .map(::resultRowToPayment)
    }

    override suspend fun findPaymentByOrderId(orderId: Long): Payment? = dbQuery {
        PaymentEntity
            .select { PaymentEntity.orderId eq orderId }
            .map(::resultRowToPayment)
            .singleOrNull()
    }

    override suspend fun updatePaymentStatusByOrderId(orderId: Long, paymentValidated: Boolean): Boolean = dbQuery {
        PaymentEntity
            .update ({PaymentEntity.orderId eq orderId }) {
                it[PaymentEntity.paymentValidated] = paymentValidated
            } > 0
    }
}