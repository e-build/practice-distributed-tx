package me.practice.event_based_arch.domain_event.core.model

class EventHandlerList(
    private val eventHandlers: MutableList<EventHandler> = mutableListOf(),
) {

    fun getEventHandlers() = eventHandlers.toList()

    fun add(eventHandler: EventHandler) {
        this.eventHandlers.add(eventHandler)
    }

    fun findHandlers(domainEventName: String): List<EventHandler> {
        return this.eventHandlers.filter { it.eventMetadata.name == domainEventName }
    }
}