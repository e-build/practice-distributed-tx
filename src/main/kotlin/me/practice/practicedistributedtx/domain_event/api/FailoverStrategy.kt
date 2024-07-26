package me.practice.practicedistributedtx.domain_event.api

enum class FailoverStrategy {
    PUBLISH_FAIL_EVENT,
    NO_ACTION,
    RETRY,
    ;
}

