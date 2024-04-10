package com.example.pet_hospital.Service.impl;

import com.aliyun.oss.OSS;
import com.example.pet_hospital.Service.AliyunOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;

@Service
public class AliyunOssService_impl implements AliyunOssService {

    private final OSS ossClient;

    // 构造方法名称与类名相匹配
    @Autowired
    public AliyunOssService_impl(OSS ossClient) {
        this.ossClient = ossClient;
    }

    @Override
    public void uploadFile(String bucketName, String objectName, InputStream inputStream) {
        ossClient.putObject(bucketName, objectName, inputStream);
        // 注意：实际操作中，应确保输入流在使用后正确关闭，以避免资源泄露
        // 这里的示例没有直接关闭流，因为流的关闭通常应由调用者管理（例如，通过try-with-resources语句）
    }

    @Override
    public InputStream downloadFile(String bucketName, String objectName) {
        return null;
    }

    // 可以根据需要实现其他与OSS相关的方法，如文件下载等
}
