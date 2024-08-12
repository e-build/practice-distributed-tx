package me.practice.event_based_arch.domain_event.core.failover

interface FailEventPublishingCustomizer {
    fun customize(function: () -> Unit)
}