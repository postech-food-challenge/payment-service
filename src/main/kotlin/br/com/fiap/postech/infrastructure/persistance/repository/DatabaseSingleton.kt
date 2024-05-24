package br.com.fiap.postech.payment_service.infrastructure.persistance.repository

import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import io.ktor.util.logging.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    private const val MAX_RETRIES = 10
    private const val RETRY_DELAY_MS = 1000L

    fun init(config: ApplicationConfig, logger: Logger) {
        val driverClassName = config.property("database.driverClassName").getString()
        val jdbcURL = config.property("database.db_host").getString()
        val user = config.property("database.user").getString()
        val password = config.property("database.password").getString()

        val hikariConfig = HikariConfig().apply {
            this.jdbcUrl = jdbcURL
            this.driverClassName = driverClassName
            this.username = user
            this.password = password
            this.maximumPoolSize = 10
            this.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        var attempts = 0
        var connected = false

        while (!connected) {
            try {
                val dataSource = HikariDataSource(hikariConfig)
                Database.connect(dataSource)
                transaction {
                    SchemaUtils.create(PaymentEntity)
                }
                logger.info("Database connection and migration successful.")
                connected = true
            } catch (ex: Exception) {
                attempts++
                logger.error(
                    "Error during database connection or migration: ${ex.message}. Attempt $attempts of $MAX_RETRIES",
                    ex
                )
                if (attempts < MAX_RETRIES) {
                    Thread.sleep(RETRY_DELAY_MS)
                } else {
                    throw ex
                }
            }
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}