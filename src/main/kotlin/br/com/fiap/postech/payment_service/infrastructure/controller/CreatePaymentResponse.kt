package br.com.fiap.postech.payment_service.infrastructure.controller

class CreatePaymentResponse (
    val price: Int,
    val qrData: String,
    val inStoreOrderId: String
)