package br.com.fiap.postech.payment_service.infrastructure.persistance.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object PaymentEntity : Table() {
    val paymentId = long("id").autoIncrement()
    val orderId = long("order_id")
    val totalAmount = integer("total_amount")
    val description = varchar("description", 2048).nullable()
    val qrCode =  varchar("qrCode", 2048)
    val paymentStatus = varchar("status", 20)
    val createdAt = timestamp("created_at")
    val expireAt = timestamp("expire_at")
    val lastModified = timestamp("last_modified")

    override val primaryKey = PrimaryKey(paymentId)
}