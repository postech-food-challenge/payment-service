package br.com.fiap.postech.payment_service.infrastructure.persistance.entity

import org.jetbrains.exposed.sql.Table
import java.util.UUID

data class PaymentEntity(val id: UUID, val amount: Int, val description: String)

object Payments : Table() {
    val id = varchar("id", 36)
    val title = integer("amount")
    val body = varchar("description", 1024)

    override val primaryKey = PrimaryKey(id)
}