package me.practice.practicedistributedtx.domain_event.core

import jakarta.annotation.PostConstruct
import me.practice.practicedistributedtx.domain_event.api.DomainEventListener
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation

@Component
class EventHandleListenerScanner(
    private val applicationContext: ApplicationContext,
    private val eventHandlerFactory: EventHandlerFactory,
) {

    private val SELF_REFERENCE_CLASS_UPPER_NAMES = listOf(
        EventHandlerFactory::class.java.simpleName.uppercase(),
        EventHandleListenerScanner::class.java.simpleName.uppercase()
    )

    @PostConstruct
    fun init() {
        applicationContext.beanDefinitionNames.forEach { beanName ->
            // 순환참조 방어
            if (selfReferenceBeans(beanName)) {
                return@forEach
            }
            // 이벤트 핸들러 스캔
            val bean = applicationContext.getBean(beanName)
            scanFunctionsAnnotatedEventListener(bean).forEach { function ->
                eventHandlerFactory.add(
                    bean = bean,
                    handlerFunction = function,
                    eventMetadata = function.findAnnotation<DomainEventListener>()!!
                )
            }
        }
    }

    private fun selfReferenceBeans(beanName: String): Boolean {
        return beanName.uppercase() in SELF_REFERENCE_CLASS_UPPER_NAMES
    }

    private fun scanFunctionsAnnotatedEventListener(bean: Any): List<KFunction<*>> {
        return try {
            bean::class.declaredFunctions.filter { it.annotations.any { annotation -> annotation is DomainEventListener } }
        } catch (e: Exception) {
            listOf()
        } catch (e: Error) {
            listOf()
        }
    }

}