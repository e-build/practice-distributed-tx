package me.practice.practicedistributedtx.domain_event.core.failover.impl

import me.practice.practicedistributedtx.domain_event.core.failover.FailEventPublishingCustomizer

class SimpleFailEventPublishingCustomizer(

) : FailEventPublishingCustomizer {

    override fun customize(function: () -> Unit) {

    }

}