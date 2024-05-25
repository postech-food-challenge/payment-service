package br.com.fiap.postech.infrastructure.persistance.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object PaymentEntity : Table() {
    val paymentId = long("id").autoIncrement()
    val orderId = uuid("order_id")
    val totalAmount = integer("total_amount")
    val description = varchar("description", 2048).nullable()
    val qrCode =  varchar("qrCode", 2048)
    val paymentStatus = varchar("payment_status", 30)
    val createdAt = varchar("created_at", 128)
    val lastModified = varchar("last_modified", 128)

    override val primaryKey = PrimaryKey(paymentId)
}