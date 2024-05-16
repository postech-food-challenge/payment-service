package br.com.fiap.postech.payment_service.infrastructure.controller

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configurePaymentController(

) {
    routing {
        route("/v1/payments") {
            get("/{orderId}"){

            }
            put("/webhook"){

            }
            post{

            }
        }
    }
}
