package br.com.fiap.postech.payment_service.infrastructure.controller

import br.com.fiap.postech.payment_service.infrastructure.persistance.repository.PaymentRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*


fun Application.configurePaymentController(repository: PaymentRepository) {
    routing {
        route("/v1/payments"){
            get {
                call.respond(repository.allPayments().toString())
            }

            post {
                val formParameters = call.receiveParameters()
                val totalAmount = formParameters.getOrFail("totalAmount").toInt()
                val items = formParameters.getOrFail("items")
                val payment = repository.addNewPayment(totalAmount, items)
                call.respond(payment.toString())
            }
        }
    }
}
