package com.claire7.wechat.service;

import com.claire7.wechat.TuringChatRobotResponse;
import com.claire7.wechat.util.JsonUtil;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongnanhuang209074 on 2018/5/12.
 */
@Service
public class ChatRobotService {

    //http://www.tuling123.com/openapi/api?key=8221e96515ef4aceaa4ead9f7861e854&info=北京天气
    private String url = "http://www.tuling123.com/openapi/api";
    private String apiKey = "8221e96515ef4aceaa4ead9f7861e854";
    private IService iService = new WxService();

    public String get(String content){
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("key",apiKey);
            params.put("info",content);
            String responsestr = iService.get(url, params);
            TuringChatRobotResponse response = JsonUtil.fromJson(responsestr, TuringChatRobotResponse.class);
            return response.getText();
        }catch (Exception e){
            return null;
        }
    }

}
