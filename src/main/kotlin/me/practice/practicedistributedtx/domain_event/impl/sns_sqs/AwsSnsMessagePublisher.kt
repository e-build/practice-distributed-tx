package me.practice.practicedistributedtx.domain_event.impl.sns_sqs

import me.practice.practicedistributedtx.domain_event.core.EventMessagePublisher
import me.practice.practicedistributedtx.domain_event.core.model.EventMessagingPayload
import me.practice.practicedistributedtx.util.JsonUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class AwsSnsMessagePublisher(
    @Value("\${spring.cloud.aws.sns.topic.arn}")
    private val snsTopicARN: String,
    private val snsClient: SnsClient,
) : EventMessagePublisher {

    override fun publish(payload: EventMessagingPayload) {
        snsClient.publish(
            PublishRequest.builder()
                .topicArn(snsTopicARN)
                .subject("sns 전송 테스트")
                .message(JsonUtils.serialize(payload))
                .build()
        )
    }


}