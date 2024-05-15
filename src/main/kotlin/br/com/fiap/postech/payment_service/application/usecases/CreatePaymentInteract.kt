package br.com.fiap.postech.payment_service.application.usecases

import br.com.fiap.postech.payment_service.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.payment_service.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.payment_service.infrastructure.persistance.entity.PaymentEntity

class CreatePaymentInteract(private val paymentGateway: MercadoPagoGateway) {
    fun createPayment(payment: PaymentEntity) {
        val mercadoPagoRequest = MercadoPagoRequest.fromPayment(payment)
        paymentGateway.createPayment(mercadoPagoRequest)

    }
}