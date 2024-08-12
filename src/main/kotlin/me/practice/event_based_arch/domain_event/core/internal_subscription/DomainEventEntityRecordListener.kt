package me.practice.event_based_arch.domain_event.core.internal_subscription

import com.fasterxml.jackson.databind.ObjectMapper
import me.practice.event_based_arch.domain_event.api.DomainEvent
import me.practice.event_based_arch.domain_event.core.outbox.DomainEventEntity
import me.practice.event_based_arch.domain_event.core.outbox.DomainEventRecordRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DomainEventEntityRecordListener(
    private val domainEventRecordRepository: DomainEventRecordRepository,
    private val mapper: ObjectMapper,
) {

    @EventListener
    @Transactional
    fun listen(event: DomainEvent<*>) {
        domainEventRecordRepository.saveAndFlush(
            DomainEventEntity(
                id = event.id,
                name = event.name,
                userId = event.userId,
                payload = mapper.writeValueAsString(event),
            )
        )
    }
}