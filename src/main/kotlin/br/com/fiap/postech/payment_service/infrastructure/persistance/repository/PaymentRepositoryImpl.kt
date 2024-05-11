package br.com.fiap.postech.payment_service.infrastructure.persistance.repository

import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity
import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.Payments
import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.DatabaseSingleton.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PaymentRepositoryImpl: PaymentRepository{

    private fun resultRowToPayment(row: ResultRow) = PaymentEntity(
        id = row[Payments.id],
        totalAmount = row[Payments.totalAmount],
        items = row[Payments.items],
    )
    override suspend fun allPayments(): List<PaymentEntity> = dbQuery {
        Payments.selectAll().map(::resultRowToPayment)
    }
    override suspend fun payment(id: Long): PaymentEntity? = dbQuery {
        Payments
            .select { Payments.id eq id }
            .map(::resultRowToPayment)
            .singleOrNull()
    }

    override suspend fun addNewPayment(totalAmount: Int, items: String): PaymentEntity? = dbQuery {
        val insertStatement = Payments.insert {
            it[Payments.totalAmount] = totalAmount
            it[Payments.items] = items
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPayment)
    }

    override suspend fun deletePayment(id: Long): Boolean = dbQuery {
        Payments.deleteWhere { Payments.id eq id } > 0
    }
}