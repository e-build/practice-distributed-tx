package me.practice.practicedistributedtx.domain_event.impl.sns_sqs

import io.awspring.cloud.sqs.annotation.SqsListener
import me.practice.practicedistributedtx.domain_event.core.EventMessageBroadcaster
import me.practice.practicedistributedtx.domain_event.core.EventMessageListener
import me.practice.practicedistributedtx.domain_event.core.model.EventMessagingPayload
import me.practice.practicedistributedtx.domain_event.core.outbox.DomainEventRecordRepository
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AwsSqsMessageListener(
    private val eventMessageBroadcaster: EventMessageBroadcaster,
    private val domainEventRecordRepository: DomainEventRecordRepository,
) : EventMessageListener {

    /**
     * 1. Listen message from AWS SQS
     * 2. Update transaction outbox record to published
     * 3. Spread event to Handlers
     */
    @SqsListener(value = ["\${spring.cloud.aws.sqs.queue.name}"])
    override fun listen(
        @Payload payload: EventMessagingPayload
    ) {
        val domainEventEntity = domainEventRecordRepository.findById(payload.domainEvent.id).orElse(null) ?: return
        domainEventEntity.completedPublishing()
        domainEventRecordRepository.saveAndFlush(domainEventEntity)

        eventMessageBroadcaster.broadcast(payload.domainEvent)
    }
}




