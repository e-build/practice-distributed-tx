package me.practice.practicedistributedtx.application.delivery

import me.practice.practicedistributedtx.domain_event.api.DomainEvent
import me.practice.practicedistributedtx.domain_event.api.DomainEventListener
import me.practice.practicedistributedtx.domain_event.api.FailoverStrategy
import me.practice.practicedistributedtx.util.JsonUtils

class DeliveryManager {

    @DomainEventListener(
        name = "ORDERED",
        failoverStrategy = FailoverStrategy.NO_ACTION
    )
    fun handle(domainEvent: DomainEvent<String>) {
        println("DeliveryManager.handle : ${JsonUtils.serialize(domainEvent)}")
    }
}