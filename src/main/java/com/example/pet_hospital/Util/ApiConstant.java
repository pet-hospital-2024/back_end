package com.example.pet_hospital.Util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.Token;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiConstant {

    /**
     * ERNIE_BOT_TURBO 发起会话接口
     */
    public static final String ERNIE_BOT_TURBO_INSTANT = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=";

    public static String getToken(String appKey, String secretKey) {
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id="
                + appKey + "&client_secret=" + secretKey;
        String s = HttpUtil.get(url);
        Token bean = JSONUtil.toBean(s, Token.class);
        return bean.getAccess_token();
    }
}
