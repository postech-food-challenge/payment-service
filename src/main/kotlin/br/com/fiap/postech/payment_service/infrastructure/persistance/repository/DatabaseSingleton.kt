package br.com.fiap.postech.payment_service.infrastructure.persistance.repository

import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("database.driverClassName").getString()
        val jdbcURL = config.property("database.db_host").getString()
        val user = config.property("database.user").getString()
        val password = config.property("database.password").getString()
        val database = Database.connect(jdbcURL, driverClassName, user, password)

        transaction(database) {
            SchemaUtils.create(PaymentEntity)
        }
    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}