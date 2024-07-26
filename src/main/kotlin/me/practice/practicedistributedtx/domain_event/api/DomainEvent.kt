package me.practice.practicedistributedtx.domain_event.api

import org.springframework.context.ApplicationEvent
import java.util.*

class DomainEvent<T : Any>(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val userId: String,
    val data: T,
) : ApplicationEvent(data)