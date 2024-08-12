package me.practice.event_based_arch.domain_event.impl.sns_sqs

import io.awspring.cloud.sqs.annotation.SqsListener
import me.practice.event_based_arch.domain_event.core.EventMessageBroadcaster
import me.practice.event_based_arch.domain_event.core.EventMessageSubscriber
import me.practice.event_based_arch.domain_event.core.model.EventMessagingPayload
import me.practice.event_based_arch.domain_event.core.outbox.DomainEventEntity
import me.practice.event_based_arch.domain_event.core.outbox.DomainEventRecordRepository
import me.practice.event_based_arch.util.JsonUtils
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AwsSqsMessageSubscriber(
    private val eventMessageBroadcaster: EventMessageBroadcaster,
    private val domainEventRecordRepository: DomainEventRecordRepository,
) : EventMessageSubscriber {

    /**
     * 1. Listen message from AWS SQS
     * 2. Update transaction outbox record to published
     * 3. Spread event to Handlers
     */
    @SqsListener(value = ["\${spring.cloud.aws.sqs.queue.name}"])
    fun subscribe(
        @Payload sqsPayload: SnsMessage,
    ) {
        val eventMessagingPayload = JsonUtils.deserialize(sqsPayload.message, EventMessagingPayload::class.java)
        val domainEvent = readDomainEvent(eventMessagingPayload) ?: return
        domainEvent.completedPublishing()
        domainEventRecordRepository.saveAndFlush(domainEvent)

        // 이벤트 전파
        this.onEvent(eventMessagingPayload)
    }

    private fun readDomainEvent(eventMessagingPayload: EventMessagingPayload): DomainEventEntity? {
        return domainEventRecordRepository.findById(eventMessagingPayload.domainEvent.id).orElse(null)
    }

    override fun onEvent(payload: EventMessagingPayload) {
        eventMessageBroadcaster.broadcast(payload.domainEvent)
    }

}