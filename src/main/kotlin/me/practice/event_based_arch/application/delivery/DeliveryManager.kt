package me.practice.event_based_arch.application.delivery

import me.practice.event_based_arch.domain_event.api.DomainEvent
import me.practice.event_based_arch.domain_event.api.DomainEventListener
import me.practice.event_based_arch.domain_event.api.FailoverStrategy
import me.practice.event_based_arch.util.JsonUtils

class DeliveryManager {

    @DomainEventListener(
        name = "ORDERED",
        failoverStrategy = FailoverStrategy.NO_ACTION
    )
    fun handle(domainEvent: DomainEvent<String>) {
        println("DeliveryManager.handle : ${JsonUtils.serialize(domainEvent)}")
    }
}