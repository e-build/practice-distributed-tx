package me.practice.practicedistributedtx.domain_event.core.failover

interface RetryCustomizer {
    fun customize(function: () -> Unit)
}