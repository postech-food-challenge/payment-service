package br.com.fiap.postech.infrastructure.gateways

import br.com.fiap.postech.application.gateways.SqsGateway
import br.com.fiap.postech.configuration.AwsConfiguration
import br.com.fiap.postech.domain.entities.PaymentStatus
import java.util.UUID
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

class SqsClientGateway (
    private val sqsClient: SqsClient,
    private val awsConfiguration: AwsConfiguration,
    private val orderStatusQueueName: String,
) : SqsGateway {
    override suspend fun updatePaymentStatusOnOrderService(orderId: UUID, paymentStatus: PaymentStatus) {
        val message = mapOf("status" to paymentStatus.name, "orderId" to orderId.toString()).toString()

        val messageRequest = SendMessageRequest.builder()
            .queueUrl(awsConfiguration.getQueueUrl(orderStatusQueueName))
            //.queueUrl("http://localhost:4566/000000000000/payment-status-update")
            .messageBody(message)
            .build()

        sqsClient.sendMessage(messageRequest)
    }
}