package com.example.pet_hospital.Util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunConfig {

    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private String ossEndpoint;

    @Value("${aliyun.vod.regionId}")
    private String regionId; // 例如 "cn-shanghai"

    /**
     * 初始化OSS客户端。
     *
     * @return OSS客户端实例
     */
    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(ossEndpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化VOD客户端。
     *
     * @return VOD客户端实例
     */
    @Bean
    public IAcsClient vodClient() {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }
}
