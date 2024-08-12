package me.practice.event_based_arch.domain_event.core.failover

interface RetryCustomizer {
    fun customize(function: () -> Unit)
}