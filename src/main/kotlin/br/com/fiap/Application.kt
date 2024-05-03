package br.com.fiap

import br.com.fiap.postech.payment_service.plugins.configureRouting
import br.com.fiap.postech.payment_service.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
