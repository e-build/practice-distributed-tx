package me.practice.practicedistributedtx.domain_event.api

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class DomainEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun publish(domainEvent: DomainEvent<*>) {
        applicationEventPublisher.publishEvent(domainEvent)
    }
}