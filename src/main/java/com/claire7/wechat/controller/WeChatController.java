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

            router.rule().event(WxConsts.EVT_CLICK)
                    .eventKey(Constant.MenuKey.TRAVEL_DESIGN).handler(TravelDesignHandler.getInstance()).next().rule()
                    .eventKey(Constant.MenuKey.TALK_ABOUT).handler(TalkAboutDesignHandler.getInstance()).end();

            // 把消息传递给路由器进行处理
            WxXmlOutMessage xmlOutMsg = router.route(wx);
            if (xmlOutMsg != null)
                out.print(xmlOutMsg.toXml());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

}
