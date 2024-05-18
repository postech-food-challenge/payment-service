package br.com.fiap.postech.payment_service.infrastructure.controller

import br.com.fiap.postech.payment_service.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.payment_service.application.usecases.UpdatePaymentInteract
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePaymentController(
    createPaymentInteract: CreatePaymentInteract,
    updatePaymentInteract: UpdatePaymentInteract
) {
    routing {
        route("/v1/payment") {
            put("/webhook"){
                val paymentRequest = call.receive<UpdatePaymentRequest>()
                updatePaymentInteract.updatePaymentStatusByOrderId(paymentRequest)
                call.respond(HttpStatusCode.OK)
            }
            post{
                val paymentRequest = call.receive<CreatePaymentRequest>()
                val paymentResponse = createPaymentInteract.createPayment(paymentRequest)
                call.respond(HttpStatusCode.Created, paymentResponse)
            }
        }
    }
}
