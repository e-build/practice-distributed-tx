package me.practice.event_based_arch.domain_event.core.failover

import me.practice.event_based_arch.domain_event.core.failover.impl.SimpleFailEventPublishingCustomizer
import me.practice.event_based_arch.domain_event.core.failover.impl.SimpleRetryCustomizer
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FailoverConfiguration {

    @Bean
    @ConditionalOnMissingBean(FailEventPublishingCustomizer::class)
    fun simpleFailEventPublishingCustomizer(): FailEventPublishingCustomizer {
        return SimpleFailEventPublishingCustomizer()
    }

    @Bean
    @ConditionalOnMissingBean(RetryCustomizer::class)
    fun simpleRetryCustomizer(): RetryCustomizer {
        return SimpleRetryCustomizer()
    }

}