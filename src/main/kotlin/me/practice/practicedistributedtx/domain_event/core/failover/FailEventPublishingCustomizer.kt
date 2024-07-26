package me.practice.practicedistributedtx.domain_event.core.failover

interface FailEventPublishingCustomizer {
    fun customize(function: () -> Unit)
}