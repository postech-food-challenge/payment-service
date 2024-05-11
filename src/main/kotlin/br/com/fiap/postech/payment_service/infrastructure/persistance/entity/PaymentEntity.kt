package br.com.fiap.postech.payment_service.infrastructure.persistance.entity

import org.jetbrains.exposed.sql.Table

data class PaymentEntity(val id: Long, val totalAmount: Int, val items: String)

//class Item(
//    val description: String? = null,
//    val quantity: Int? = null,
//    val totalAmount: Int? = null,
//)

object Payments : Table() {
    val id = long("id").autoIncrement()
    val totalAmount = integer("totalAmount")
    val items = varchar("items", 2048)

    override val primaryKey = PrimaryKey(id)
}