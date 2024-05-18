package br.com.fiap.postech.payment_service.infrastructure.client.mercadopago

import br.com.fiap.postech.payment_service.infrastructure.controller.CreatePaymentRequest
import kotlinx.serialization.Serializable

@Serializable
class MercadoPagoRequest(
    val cashOut: CashOut,
    val description: String? = null,
    val externalReference: String? = null,
    val items: List<ItemRequest>,
    val notificationUri: String? = null,
    val sponsor: Sponsor? = null,
    val title: String? = null,
    val totalAmount: Int? = null
) {
    @Serializable
    class CashOut(
        val amount: Int? = null
    )
    @Serializable
    class ItemRequest(
        val skuNumber: String? = null,

        val category: String? = null,
        val title: String? = null,
        val description: String? = null,

        val unitPrice: Int? = null,

        val quantity: Int? = null,

        val totalAmount: Int? = null,
    )
    @Serializable
    class Sponsor(
        val id: String
    )

    companion object {
        fun fromPaymentRequest(createPaymentRequest: CreatePaymentRequest) =
            MercadoPagoRequest(
                cashOut = CashOut(createPaymentRequest.totalAmount),
                items = emptyList(),
                totalAmount = createPaymentRequest.totalAmount
            )
    }
}