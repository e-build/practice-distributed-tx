package me.practice.event_based_arch.domain_event.impl.sns_sqs

import me.practice.event_based_arch.domain_event.core.EventMessagePublisher
import me.practice.event_based_arch.domain_event.core.model.EventMessagingPayload
import me.practice.event_based_arch.util.JsonUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class AwsSnsMessagePublisher(
    private val snsClient: SnsClient,
) : EventMessagePublisher {

    @Value("\${foo.aws.sns.topic.arn}")
    lateinit var snsTopicARN: String

    override fun publish(payload: EventMessagingPayload) {
        snsClient.publish(
            PublishRequest.builder()
                .topicArn(snsTopicARN)
                .subject("sns message subject")
                .messageGroupId("")
                .messageDeduplicationId("")
                .message(JsonUtils.serialize(payload))
                .build()
        )
    }


}