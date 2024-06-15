package br.com.fiap.postech.infrastructure.controller


import br.com.fiap.postech.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.application.usecases.GetPaymentInteract
import br.com.fiap.postech.application.usecases.UpdatePaymentInteract
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID
import org.koin.ktor.ext.inject

fun Application.configurePaymentController() {
    val createPaymentInteract: CreatePaymentInteract by inject()
    val getPaymentInteract: GetPaymentInteract by inject()
    val updatePaymentInteract: UpdatePaymentInteract by inject()

    routing {
        route("/v1/payment") {
            put("/webhook") {
                val paymentRequest = call.receive<UpdatePaymentRequest>()
                updatePaymentInteract.updatePaymentStatusByOrderId(paymentRequest)
                call.respond(HttpStatusCode.OK)
            }
            post {
                val paymentRequest = call.receive<CreatePaymentRequest>()
                val paymentResponse = createPaymentInteract.createPayment(paymentRequest)
                call.respond(HttpStatusCode.Created, paymentResponse)
            }
            get("/order/{orderId}") {
                val orderId = UUID.fromString(call.parameters["orderId"])
                val paymentResponse = getPaymentInteract.getPaymentByOrderId(orderId)

                paymentResponse?.let {
                    call.respond(HttpStatusCode.OK, paymentResponse)
                } ?: run {
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }
    }
}
