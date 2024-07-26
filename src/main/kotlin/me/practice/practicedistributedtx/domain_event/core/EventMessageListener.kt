package me.practice.practicedistributedtx.domain_event.core

import me.practice.practicedistributedtx.domain_event.core.model.EventMessagingPayload

interface EventMessageListener {
    fun listen(payload: EventMessagingPayload)
}