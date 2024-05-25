package br.com.fiap.postech.application.usecases

import br.com.fiap.postech.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.domain.entities.Payment
import br.com.fiap.postech.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.infrastructure.controller.CreatePaymentRequest
import br.com.fiap.postech.infrastructure.controller.CreatePaymentResponse

class CreatePaymentInteract(
    private val mercadoPago: MercadoPagoGateway,
    private val paymentGateway: PaymentGateway,
) {
    suspend fun createPayment(paymentRequest: CreatePaymentRequest): CreatePaymentResponse {
        val mercadoPagoRequest = MercadoPagoRequest.fromPaymentRequest(paymentRequest)
        val mercadoPagoResponse = mercadoPago.createPayment(mercadoPagoRequest)

        val payment = Payment(
            orderId = paymentRequest.orderId,
            totalAmount = paymentRequest.totalAmount,
            description = paymentRequest.description,
            qrData = mercadoPagoResponse.qrData
        )
        paymentGateway.save(payment)

        return CreatePaymentResponse(payment.totalAmount, payment.qrData, payment.orderId)
    }
}