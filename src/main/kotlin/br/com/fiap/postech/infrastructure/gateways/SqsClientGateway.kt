package br.com.fiap.postech.infrastructure.gateways

import br.com.fiap.postech.application.gateways.SqsGateway
import br.com.fiap.postech.configuration.AwsConfiguration
import br.com.fiap.postech.infrastructure.gateways.dto.PaymentStatusUpdateDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

class SqsClientGateway (
    private val sqsClient: SqsClient,
    private val awsConfiguration: AwsConfiguration
) : SqsGateway {
    override suspend fun updatePaymentStatusOnOrderService(dto: PaymentStatusUpdateDTO) {

        val messageRequest = SendMessageRequest.builder()
            .queueUrl(awsConfiguration.paymentStatusUpdateQueueUrl)
            .messageBody(Json.encodeToString(dto))
            .build()

        sqsClient.sendMessage(messageRequest)
    }
}