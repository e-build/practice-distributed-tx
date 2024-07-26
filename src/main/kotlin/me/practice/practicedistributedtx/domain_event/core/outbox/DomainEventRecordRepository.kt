package me.practice.practicedistributedtx.domain_event.core.outbox

import org.springframework.data.jpa.repository.JpaRepository

interface DomainEventRecordRepository : JpaRepository<DomainEventEntity, String>