package me.practice.event_based_arch.domain_event.api

enum class FailoverStrategy {
    PUBLISH_FAIL_EVENT,
    NO_ACTION,
    RETRY,
    ;
}

