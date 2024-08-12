package me.practice.event_based_arch.domain_event.api

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DomainEventListener(
    val name: String,
    val failoverStrategy: FailoverStrategy = FailoverStrategy.NO_ACTION,
    val retryCount: Int = 3,
    val retryTimeUnit: TimeUnit = TimeUnit.MINUTES,
)