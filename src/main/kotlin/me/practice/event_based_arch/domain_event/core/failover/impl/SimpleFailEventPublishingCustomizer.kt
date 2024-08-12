package me.practice.event_based_arch.domain_event.core.failover.impl

import me.practice.event_based_arch.domain_event.core.failover.FailEventPublishingCustomizer

class SimpleFailEventPublishingCustomizer(

) : FailEventPublishingCustomizer {

    override fun customize(function: () -> Unit) {

    }

}