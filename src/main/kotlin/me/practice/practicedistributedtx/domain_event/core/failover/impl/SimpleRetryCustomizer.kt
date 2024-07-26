package me.practice.practicedistributedtx.domain_event.core.failover.impl

import me.practice.practicedistributedtx.domain_event.core.failover.RetryCustomizer

class SimpleRetryCustomizer(
) : RetryCustomizer {

    override fun customize(function: () -> Unit) {

    }
}