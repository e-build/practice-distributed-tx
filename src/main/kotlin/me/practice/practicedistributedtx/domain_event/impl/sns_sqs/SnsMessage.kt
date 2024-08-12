package me.practice.practicedistributedtx.domain_event.impl.sns_sqs

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class SnsMessage(
    @JsonProperty("Type")
    val type: String,
    @JsonProperty("MessageId")
    val messageId: String,
    @JsonProperty("SequenceNumber")
    val sequenceNumber: String,
    @JsonProperty("TopicArn")
    val topicArn: String,
    @JsonProperty("Subject")
    val subject: String,
    @JsonProperty("Message")
    val message: String,
    @JsonProperty("Timestamp")
    val timestamp: ZonedDateTime,
    @JsonProperty("UnsubscribeURL")
    val unsubscribeURL: String,
)