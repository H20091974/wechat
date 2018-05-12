package com.claire7.wechat.handler;

import com.claire7.wechat.util.Constant;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.bean.WxXmlOutNewsMessage;
import com.soecode.wxtools.bean.outxmlbuilder.NewsBuilder;
import com.soecode.wxtools.exception.WxErrorException;

import java.util.Map;

/**
 * Created by zhongnanhuang209074 on 2018/5/12.
 */
public class TalkAboutDesignHandler implements WxMessageHandler {


    private static TalkAboutDesignHandler instance = null;

    private boolean isRun = false;


    public static synchronized TalkAboutDesignHandler getInstance(){
        if (instance == null) {
            instance = new TalkAboutDesignHandler();
        }
        return instance;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setIsRun(boolean isRun) {
        this.isRun = isRun;
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxXmlMessage, Map<String, Object> map, IService iService) throws WxErrorException {
        WxXmlOutMessage response = null;
        if (!isRun()) {
            setIsRun(true);
            response = execute(wxXmlMessage);
            setIsRun(false);
        }
        return response;
    }

    private WxXmlOutMessage execute(WxXmlMessage wxMessage) {
        try {
                NewsBuilder newsBuilder = WxXmlOutMessage.NEWS();
                WxXmlOutNewsMessage.Item item = new WxXmlOutNewsMessage.Item();
                item.setTitle("当小清新遇见盛世古城，月坨岛与滦州的两日之旅（下）");
                item.setDescription("短短的京郊两日游就这样结束啦，旅程虽短，给心灵放个假。下次，我们再会。");
                item.setUrl("https://mp.weixin.qq.com/s/L5xNkvYKKR8qurLeYe3pEQ");
                item.setPicUrl("https://mmbiz.qpic.cn/mmbiz_jpg/ArpjhGmjianziaPCS7sGbRJHibq6YgYDtfOobc4roMibRvrGyrtB4oxZickcP8ZLGZOibh0IvtZpy3A7tjSZcl0uicicrA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                newsBuilder.addArticle(item);
                return newsBuilder.toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName()).build();
        } catch (Exception e) {
        }
        return WxXmlOutMessage.TEXT().content(Constant.ResponseConst.DEFAULE_TEXT).toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName()).build();
    }
}
