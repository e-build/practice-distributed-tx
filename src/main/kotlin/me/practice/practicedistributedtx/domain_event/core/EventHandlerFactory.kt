package me.practice.practicedistributedtx.domain_event.core

import me.practice.practicedistributedtx.domain_event.api.DomainEventListener
import me.practice.practicedistributedtx.domain_event.core.model.EventHandler
import me.practice.practicedistributedtx.domain_event.core.model.EventHandlerList
import org.springframework.stereotype.Component
import kotlin.reflect.KFunction

@Component
class EventHandlerFactory {

    private val eventHandlerList: EventHandlerList = EventHandlerList()

    fun add(
        bean: Any,
        handlerFunction: KFunction<*>,
        eventMetadata: DomainEventListener,
    ) {
        eventHandlerList.add(
            EventHandler(
                bean = bean,
                function = handlerFunction,
                eventMetadata = eventMetadata
            )
        )
    }

    fun getHandlers(eventName: String): List<EventHandler> {
        return this.eventHandlerList.findHandlers(eventName)
    }
}