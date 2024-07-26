package me.practice.practicedistributedtx.domain_event.core.outbox

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.ZonedDateTime

@Entity
class DomainEventEntity(
    @Id
    val id: String,
    val name: String,
    val userId: String,
    val payload: String,
    var published: Boolean = false,
    var publishedAt: ZonedDateTime? = null,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
) {
    fun completedPublishing() {
        published = true
        publishedAt = ZonedDateTime.now()
    }
}