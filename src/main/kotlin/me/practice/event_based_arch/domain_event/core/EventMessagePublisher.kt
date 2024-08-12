package me.practice.event_based_arch.domain_event.core

import me.practice.event_based_arch.domain_event.core.model.EventMessagingPayload

interface EventMessagePublisher {
    fun publish(payload: EventMessagingPayload)
}