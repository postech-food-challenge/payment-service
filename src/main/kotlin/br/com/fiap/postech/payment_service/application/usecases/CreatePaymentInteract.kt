package br.com.fiap.postech.payment_service.application.usecases

import br.com.fiap.postech.payment_service.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity

class CreatePaymentInteract(private val mercadoPago: MercadoPagoGateway) {
    fun createPayment(paymentRequest: CreatePaymentRequest) {
        val mercadoPagoRequest = MercadoPagoRequest.fromPayment(paymentRequest)
        val mercadoPagoResponse = mercadoPago.createPayment(mercadoPagoRequest)
    }
}