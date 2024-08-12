package me.practice.event_based_arch.domain_event.core

import me.practice.event_based_arch.domain_event.core.model.EventMessagingPayload


interface EventMessageSubscriber {
    /**
     * 1. Listen message from AWS SQS
     * 2. Update transaction outbox record to published
     * 3. Spread event to Handlers
     */
    fun onEvent(payload: EventMessagingPayload)
}