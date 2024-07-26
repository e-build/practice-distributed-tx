package me.practice.practicedistributedtx.domain_event.core.internal_subscription

import me.practice.practicedistributedtx.domain_event.core.EventMessagePublisher
import me.practice.practicedistributedtx.domain_event.api.DomainEvent
import me.practice.practicedistributedtx.domain_event.core.model.EventMessagingPayload
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class EventMessagePublishingListener(
    private val eventMessagePublisher: EventMessagePublisher,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun listen(event: DomainEvent<*>) {
        eventMessagePublisher.publish(
            EventMessagingPayload.from(event)
        )
    }

}