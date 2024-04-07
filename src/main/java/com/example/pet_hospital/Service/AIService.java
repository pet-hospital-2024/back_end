package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.ErnieBotTurboResponse;
import com.example.pet_hospital.Entity.ErnieBotTurboStreamParam;
import okhttp3.sse.EventSourceListener;

public interface AIService {

    /**
     * 发送同步请求到 AI 服务
     *
     * @param param 请求参数
     * @return AI 服务的响应
     */
    ErnieBotTurboResponse ernieBotTurbo(ErnieBotTurboStreamParam param);

    /**
     * 通过流的方式请求 AI 服务
     *
     * @param param 请求参数
     * @param eventSourceListener 事件源监听器
     */
    void ernieBotTurboStream(ErnieBotTurboStreamParam param, EventSourceListener eventSourceListener);

}
