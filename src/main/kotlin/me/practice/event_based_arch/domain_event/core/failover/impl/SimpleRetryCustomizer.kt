package me.practice.event_based_arch.domain_event.core.failover.impl

import me.practice.event_based_arch.domain_event.core.failover.RetryCustomizer

class SimpleRetryCustomizer(
) : RetryCustomizer {

    override fun customize(function: () -> Unit) {

    }
}