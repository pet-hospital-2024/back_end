package com.example.pet_hospital.Util;

import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.port}")
    private int port;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.secure}")
    private boolean secure;

    // 这里你可以添加getter方法来获取配置值，例如桶名和文件大小限制，
    // 以便在其他部分的代码中使用它们，如服务层或控制层。
    @Getter
    @Value("${minio.bucket-name}")
    private String bucketName;

    @Getter
    @Value("${minio.image-size}")
    private long imageSize;

    @Getter
    @Value("${minio.file-size}")
    private long fileSize;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
    }

}


