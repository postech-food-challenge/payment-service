package br.com.fiap.postech.infrastructure.listener

import br.com.fiap.postech.configuration.AwsConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest

class PaymentStatusUpdateListener(
    private val sqsClient: SqsClient,
    private val awsConfiguration: AwsConfiguration
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun startListening() {
        val queueUrl = awsConfiguration.paymentStatusUpdateQueueUrl
        scope.launch {
            while (isActive) {
                try {
                    val receiveMessageRequest = ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10)
                        .waitTimeSeconds(20)
                        .build()

                    val messages = sqsClient.receiveMessage(receiveMessageRequest).messages()

                    for (message in messages) {
                        println("Received message: ${message.body()}")
                        val deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build()
                        sqsClient.deleteMessage(deleteMessageRequest)
                    }
                } catch (e: Exception) {
                    println("Error receiving messages: ${e.message}")
                }
            }
        }
    }
}