package com.example.pet_hospital.Service;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsResponse;

public interface AliyunVodService {

    // 上传视频并获取上传地址和凭证
    CreateUploadVideoResponse createUploadVideo(String fileName, String title) throws ClientException;

    // 提交视频转码作业
    SubmitTranscodeJobsResponse submitTranscodeJobs(String videoId, String templateGroupId) throws ClientException;

    // 删除视频
    DeleteVideoResponse deleteVideo(String videoId) throws ClientException;



}
