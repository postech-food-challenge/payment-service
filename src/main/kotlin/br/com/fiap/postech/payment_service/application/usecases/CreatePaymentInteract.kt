package br.com.fiap.postech.payment_service.application.usecases

import br.com.fiap.postech.payment_service.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.payment_service.infrastructure.controller.CreatePaymentResponse
import br.com.fiap.postech.payment_service.infrastructure.controller.CreatePaymentRequest

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