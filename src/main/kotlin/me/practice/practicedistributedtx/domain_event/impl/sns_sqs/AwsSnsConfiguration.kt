package me.practice.practicedistributedtx.domain_event.impl.sns_sqs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient


@Configuration
class AwsSnsConfiguration(
    @Value("\${spring.cloud.aws.credentials.access-key}")
    private val awsAccessKey: String,
    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private val awsSecretKey: String,
    @Value("\${spring.cloud.aws.region.static}")
    private val awsRegion: String,
) {

    @Bean
    fun getSnsClient(): SnsClient {
        return SnsClient.builder()
            .region(Region.of(awsRegion)) // 리전 설정 추가
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(awsAccessKey, awsSecretKey)
                )
            )
            .build()
    }
}