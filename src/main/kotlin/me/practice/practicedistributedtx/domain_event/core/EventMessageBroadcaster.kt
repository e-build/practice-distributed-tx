package me.practice.practicedistributedtx.domain_event.core

import com.fasterxml.jackson.databind.ObjectMapper
import me.practice.practicedistributedtx.domain_event.api.DomainEvent
import me.practice.practicedistributedtx.domain_event.api.FailoverStrategy.*
import me.practice.practicedistributedtx.domain_event.core.failover.FailEventPublishingCustomizer
import me.practice.practicedistributedtx.domain_event.core.failover.RetryCustomizer
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component

@Component
class EventMessageBroadcaster(
    private val objectMapper: ObjectMapper,
    private val eventHandlerFactory: EventHandlerFactory,
    private val failEventPublishingCustomizer: FailEventPublishingCustomizer,
    private val retryCustomizer: RetryCustomizer,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor,
) {

    fun broadcast(domainEvent: DomainEvent<*>) {
        val eventHandlers = eventHandlerFactory.getHandlers(domainEvent.name)
        for (eventHandler in eventHandlers) {
            val eventData = objectMapper.readValue(
                objectMapper.writeValueAsString(domainEvent.data),
                eventHandler.function.parameters[0].type.javaClass
            )

            when (eventHandler.eventMetadata.failoverStrategy) {
                PUBLISH_FAIL_EVENT -> {
                    threadPoolTaskExecutor.execute {
                        failEventPublishingCustomizer.customize {
                            eventHandler.function.call(
                                eventHandler.bean,
                                eventData
                            )
                        }
                    }
                }

                NO_ACTION -> {
                    threadPoolTaskExecutor.execute {
                        eventHandler.function.call(
                            eventHandler.bean,
                            eventData
                        )
                    }
                }

                RETRY -> {
                    threadPoolTaskExecutor.execute {
                        retryCustomizer.customize {
                            eventHandler.function.call(
                                eventHandler.bean,
                                eventData
                            )
                        }
                    }
                }
            }
            TODO("Failover 정책 구현 필요, 비동기 실행 구현 필요")
        }
    }
}