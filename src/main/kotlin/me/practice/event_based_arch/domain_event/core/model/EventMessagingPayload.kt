package me.practice.event_based_arch.domain_event.core.model

import me.practice.event_based_arch.domain_event.api.DomainEvent
import java.time.ZonedDateTime

data class EventMessagingPayload(
    val domainEvent: DomainEvent<*>,
    val createdAt: ZonedDateTime = ZonedDateTime.now()
) {
    companion object {
        fun from(event: DomainEvent<*>): EventMessagingPayload {
            return EventMessagingPayload(
                domainEvent = event,
            )
        }
    }
}