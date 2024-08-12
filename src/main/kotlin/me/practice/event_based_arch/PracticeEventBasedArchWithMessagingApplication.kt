package me.practice.event_based_arch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PracticeEventBasedArchWithMessagingApplication

fun main(args: Array<String>) {
    runApplication<PracticeEventBasedArchWithMessagingApplication>(*args)
}
