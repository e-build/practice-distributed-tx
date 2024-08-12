package me.practice.event_based_arch.domain_event.core.model

import me.practice.event_based_arch.domain_event.api.DomainEventListener
import kotlin.reflect.KFunction

class EventHandler(
    val bean: Any,
    val function: KFunction<*>,
    val eventMetadata: DomainEventListener,
)