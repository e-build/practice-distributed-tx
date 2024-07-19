package me.practice.practicedistributedtx

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PracticeDistributedTxApplication

fun main(args: Array<String>) {
    runApplication<PracticeDistributedTxApplication>(*args)
}
