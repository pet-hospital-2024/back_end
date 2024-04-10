package com.example.pet_hospital.Service;

import java.io.InputStream;

public interface AliyunOssService {


    /**
     * 上传文件到OSS。
     *
     * @param bucketName 桶名
     * @param objectName 对象名
     * @param inputStream 文件输入流
     */
    void uploadFile(String bucketName, String objectName, InputStream inputStream);

    /**
     * 从OSS下载文件。
     *
     * @param bucketName 桶名
     * @param objectName 对象名
     * @return 文件的输入流
     */
    InputStream downloadFile(String bucketName, String objectName);


}
