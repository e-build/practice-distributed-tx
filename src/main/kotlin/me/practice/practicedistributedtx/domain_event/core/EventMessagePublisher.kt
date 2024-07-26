package me.practice.practicedistributedtx.domain_event.core

import me.practice.practicedistributedtx.domain_event.core.model.EventMessagingPayload

interface EventMessagePublisher {
    fun publish(payload: EventMessagingPayload)
}