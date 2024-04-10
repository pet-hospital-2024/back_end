package com.example.pet_hospital.Service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.example.pet_hospital.Service.AliyunVodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliyunVodService_impl implements AliyunVodService {

    private final IAcsClient vodClient;

    @Autowired
    public AliyunVodService_impl(IAcsClient vodClient) { // 构造函数名称应与类名相匹配
        this.vodClient = vodClient;
    }

    @Override
    public CreateUploadVideoResponse createUploadVideo(String fileName, String title) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title);
        request.setFileName(fileName);
        // 设置更多参数...
        return vodClient.getAcsResponse(request);
    }

    @Override
    public SubmitTranscodeJobsResponse submitTranscodeJobs(String videoId, String templateGroupId) throws ClientException {
        SubmitTranscodeJobsRequest request = new SubmitTranscodeJobsRequest();
        request.setVideoId(videoId);
        request.setTemplateGroupId(templateGroupId);
        // 设置更多参数...
        return vodClient.getAcsResponse(request);
    }

    @Override
    public DeleteVideoResponse deleteVideo(String videoId) throws ClientException {
        return null;
    }

    // 实现其他与VOD相关的方法，如获取视频播放地址等
}
