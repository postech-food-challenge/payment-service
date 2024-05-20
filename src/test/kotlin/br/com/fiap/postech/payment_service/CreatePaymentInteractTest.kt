package br.com.fiap.postech.payment_service

import br.com.fiap.postech.payment_service.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.payment_service.application.gateways.PaymentGateway
import br.com.fiap.postech.payment_service.application.usecases.CreatePaymentInteract
import br.com.fiap.postech.payment_service.domain.entities.Payment
import br.com.fiap.postech.payment_service.infrastructure.client.mercadopago.MercadoPagoResponse
import br.com.fiap.postech.payment_service.infrastructure.controller.CreatePaymentRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID

class CreatePaymentInteractTest {
    lateinit var mercadoPago: MercadoPagoGateway
    lateinit var paymentGateway: PaymentGateway
    lateinit var createPaymentInteract: CreatePaymentInteract


    @BeforeEach
    fun setUp() {
        mercadoPago = mock {}
        paymentGateway = mock {}
        createPaymentInteract = CreatePaymentInteract(mercadoPago,paymentGateway)
    }

    @Test
    fun `should successfully create a payment`() {
        runBlocking {
            val orderId = UUID.randomUUID()
            val totalAmount = 12
            val description = "batata"
            val qrData = "AA"
            val mercadoPagoResponse = MercadoPagoResponse(qrData, orderId.toString())
            val createPaymentRequest = CreatePaymentRequest(orderId, totalAmount, description)
            val payment = Payment(
                orderId = orderId,
                qrData = qrData,
                totalAmount = totalAmount
            )

            whenever(paymentGateway.save(any())).thenReturn(payment)
            whenever(mercadoPago.createPayment(any())).thenReturn(mercadoPagoResponse)

            val result = createPaymentInteract.createPayment(createPaymentRequest)

            Assertions.assertEquals(12, result.price)
            Assertions.assertEquals(qrData, result.qrData)
            Assertions.assertEquals(orderId.toString(), result.orderId.toString())
        }

    }

}