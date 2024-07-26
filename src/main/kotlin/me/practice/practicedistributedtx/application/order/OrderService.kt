package me.practice.practicedistributedtx.application.order

import me.practice.practicedistributedtx.application.DomainEventType
import me.practice.practicedistributedtx.domain_event.api.DomainEventPublisher
import me.practice.practicedistributedtx.domain_event.api.DomainEvent
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val domainEventPublisher: DomainEventPublisher,
) {

    fun order(
        userId: String,
        orderId: String,
    ) {
        // 로직
        // ...

        // 이벤트 발행
        domainEventPublisher.publish(
            DomainEvent(
                name = DomainEventType.ORDERD.name,
                userId = userId,
                data = orderId
            )
        )
    }

}