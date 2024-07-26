package me.practice.practicedistributedtx.application.stock

import me.practice.practicedistributedtx.domain_event.api.DomainEvent
import me.practice.practicedistributedtx.domain_event.api.DomainEventListener
import me.practice.practicedistributedtx.domain_event.api.FailoverStrategy
import me.practice.practicedistributedtx.util.JsonUtils
import org.springframework.stereotype.Service

@Service
class StockManager {

    @DomainEventListener(
        name = "ORDERED",
        failoverStrategy = FailoverStrategy.PUBLISH_FAIL_EVENT
    )
    fun handle(domainEvent: DomainEvent<String>) {
        println("StockManager.handle : ${JsonUtils.serialize(domainEvent)}")
    }
}