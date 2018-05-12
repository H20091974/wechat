package com.claire7.wechat.controller;

import com.claire7.wechat.handler.TalkAboutDesignHandler;
import com.claire7.wechat.handler.TravelDesignHandler;
import com.claire7.wechat.util.Constant;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.bean.WxXmlOutNewsMessage;
import com.soecode.wxtools.bean.outxmlbuilder.NewsBuilder;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhongnanhuang209074 on 2018/5/12.
 */
@RestController
@RequestMapping("/")
public class WeChatController {

    private IService iService = new WxService();

    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) {
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            return echostr;
        }
        return null;
    }

    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            WxMessageRouter router = new WxMessageRouter(iService);

            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            System.out.println("content: "+wx.getContent());
            router.rule().event(WxConsts.EVT_CLICK)
                    .eventKey(Constant.MenuKey.TRAVEL_DESIGN).handler(TravelDesignHandler.getInstance()).next().rule()
                    .eventKey(Constant.MenuKey.TALK_ABOUT).handler(TalkAboutDesignHandler.getInstance()).end();

            // 把消息传递给路由器进行处理
            WxXmlOutMessage xmlOutMsg = null;
            if ("1".equals(wx.getContent())){
                xmlOutMsg = executeText(wx);
            }else {
                xmlOutMsg = executeArticle(wx);
            }
            if (xmlOutMsg != null)
                out.print(xmlOutMsg.toXml());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }


    private WxXmlOutMessage executeArticle(WxXmlMessage wxMessage) {
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

    private WxXmlOutMessage executeText(WxXmlMessage wxMessage) {
        return WxXmlOutMessage.TEXT().content(Constant.ResponseConst.DEFAULE_TEXT).toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName()).build();
    }

}
