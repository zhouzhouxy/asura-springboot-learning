package com.asura.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzyx 2024/1/14
 */
@Configuration
public class MinioConfig {


    @Value("${minio.url}")
    private String url;
    @Value("${minio.access.name}")
    private String accessKey;
    @Value("${minio.access.secret}")
    private String secretKey;


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
