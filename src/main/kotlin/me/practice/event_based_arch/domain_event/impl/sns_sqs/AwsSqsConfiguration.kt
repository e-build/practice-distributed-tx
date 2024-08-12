package me.practice.event_based_arch.domain_event.impl.sns_sqs

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class AwsSqsConfiguration {

    @Value("\${spring.cloud.aws.credentials.access-key}")
    lateinit var awsAccessKey: String

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    lateinit var awsSecretKey: String

    @Value("\${spring.cloud.aws.region.static}")
    lateinit var awsRegion: String

    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .credentialsProvider {
                object : AwsCredentials {
                    override fun accessKeyId(): String {
                        return awsAccessKey
                    }

                    override fun secretAccessKey(): String {
                        return awsSecretKey
                    }
                }
            }
            .region(Region.of(awsRegion))
            .build()
    }

    @Bean
    fun defaultSqsListenerContainerFactory(): SqsMessageListenerContainerFactory<Any> {
        return SqsMessageListenerContainerFactory
            .builder<Any>()
            .sqsAsyncClient(sqsAsyncClient())
            .build()
    }
}