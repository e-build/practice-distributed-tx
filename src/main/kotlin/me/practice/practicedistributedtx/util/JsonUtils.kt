package me.practice.practicedistributedtx.util

import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass

object JsonUtils {

    private val objectMapper = ObjectMapper()

    fun serialize(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    fun <T : Any> deserialize(json: String, clazz: Class<T>): T {
        return objectMapper.readValue(json, clazz)
    }


}