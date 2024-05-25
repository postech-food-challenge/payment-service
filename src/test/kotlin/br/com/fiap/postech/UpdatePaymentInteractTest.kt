package br.com.fiap.postech

import br.com.fiap.postech.application.gateways.OrderServiceGateway
import br.com.fiap.postech.application.gateways.PaymentGateway
import br.com.fiap.postech.application.usecases.UpdatePaymentInteract
import br.com.fiap.postech.domain.entities.Payment
import br.com.fiap.postech.domain.entities.PaymentStatus
import br.com.fiap.postech.infrastructure.controller.UpdatePaymentRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class UpdatePaymentInteractTest {

    lateinit var orderServiceGateway: OrderServiceGateway
    lateinit var paymentGateway: PaymentGateway
    lateinit var updatePaymentInteract: UpdatePaymentInteract

    @BeforeEach
    fun setUp() {
        orderServiceGateway = mock {}
        paymentGateway = mock {}
        updatePaymentInteract = UpdatePaymentInteract(paymentGateway, orderServiceGateway)
    }

    @Test
    fun `should successfully update a payment with status PAYMENT_CONFIRMED`() {
        runBlocking {
            val orderId = UUID.randomUUID()
            val totalAmount = 12
            val qrData = "AA"
            val paymentId = 1L

            val request = UpdatePaymentRequest(paymentId, true)
            val orderIdCaptor = argumentCaptor<UUID>()
            val paymentStatusCaptor = argumentCaptor<PaymentStatus>()

            val payment = Payment(
                orderId = orderId,
                qrData = qrData,
                totalAmount = totalAmount
            )

            whenever(paymentGateway.updatePaymentStatus(any(), any())).thenReturn(payment)

            whenever(
                orderServiceGateway.updatePaymentStatusOnOrderService(
                    orderIdCaptor.capture(),
                    paymentStatusCaptor.capture()
                )
            ).thenReturn(Unit)

            updatePaymentInteract.updatePaymentStatusByOrderId(request)

            Assertions.assertEquals(orderId, orderIdCaptor.lastValue)
            Assertions.assertEquals(PaymentStatus.PAYMENT_CONFIRMED, paymentStatusCaptor.lastValue)
        }

    }

    @Test
    fun `should successfully update a payment with status PAYMENT_DENIED`() {
        runBlocking {
            val orderId = UUID.randomUUID()
            val totalAmount = 12
            val qrData = "AA"
            val paymentId = 1L

            val request = UpdatePaymentRequest(paymentId, false)
            val orderIdCaptor = argumentCaptor<UUID>()
            val paymentStatusCaptor = argumentCaptor<PaymentStatus>()

            val payment = Payment(
                orderId = orderId,
                qrData = qrData,
                totalAmount = totalAmount
            )

            whenever(paymentGateway.updatePaymentStatus(any(), any())).thenReturn(payment)

            whenever(
                orderServiceGateway.updatePaymentStatusOnOrderService(
                    orderIdCaptor.capture(),
                    paymentStatusCaptor.capture()
                )
            ).thenReturn(Unit)

            updatePaymentInteract.updatePaymentStatusByOrderId(request)

            Assertions.assertEquals(orderId, orderIdCaptor.lastValue)
            Assertions.assertEquals(PaymentStatus.PAYMENT_DENIED, paymentStatusCaptor.lastValue)
        }

    }
}