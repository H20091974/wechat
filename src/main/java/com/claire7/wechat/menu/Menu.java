package com.claire7.wechat.menu;

import com.claire7.wechat.util.Constant;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxMenu;
import com.soecode.wxtools.exception.WxErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhongnanhuang209074 on 2018/5/12.
 */
public class Menu {

    /**
     * 初始化菜单
     */
    public void initMenu(){
        IService iService = new WxService();
        WxMenu menu = new WxMenu();
        List<WxMenu.WxMenuButton> btnList = new ArrayList<>();

        //生活故事
        WxMenu.WxMenuButton btn1 = new WxMenu.WxMenuButton();
        btn1.setType(WxConsts.MENU_BUTTON_VIEW);
        btn1.setUrl("https://mp.weixin.qq.com/mp/homepage?__biz=MzU2NjU3NDM0Ng==&hid=1&sn=db3abd96ee91b26933acf2cf568c32b9&scene=18&devicetype=android-24&version=26060637&lang=zh_CN&nettype=WIFI&ascene=7&session_us=gh_2efa8da9b952");
        btn1.setKey(Constant.MenuKey.LIFE_STORY);
        btn1.setName("生活故事");

        //技术乱炖
        WxMenu.WxMenuButton btn2 = new WxMenu.WxMenuButton();
        btn2.setType(WxConsts.MENU_BUTTON_VIEW);
        btn2.setUrl("https://mp.weixin.qq.com/mp/homepage?__biz=MzU2NjU3NDM0Ng==&hid=2&sn=d6cd921ef4469cbfa09717d0ced9b36f&scene=18&devicetype=android-24&version=26060637&lang=zh_CN&nettype=WIFI&ascene=7&session_us=gh_2efa8da9b952");
        btn2.setKey(Constant.MenuKey.TECHNOLOGY);
        btn2.setName("技术乱炖");

        //一起聊聊
        WxMenu.WxMenuButton btn3 = new WxMenu.WxMenuButton();
        btn3.setName("一起聊聊");
        List<WxMenu.WxMenuButton> subList = new ArrayList<>();
        WxMenu.WxMenuButton btn3_1 = new WxMenu.WxMenuButton();
        btn3_1.setType(WxConsts.MENU_BUTTON_CLICK);
        btn3_1.setKey(Constant.MenuKey.TALK_ABOUT);
        btn3_1.setName("缘起缘由");

        WxMenu.WxMenuButton btn3_2 = new WxMenu.WxMenuButton();
        btn3_2.setType(WxConsts.MENU_BUTTON_CLICK);
        btn3_2.setKey(Constant.MenuKey.TRAVEL_DESIGN);
        btn3_2.setName("旅程设计");

        subList.addAll(Arrays.asList(btn3_1, btn3_2));
        btn3.setSub_button(subList);

        //将三个按钮设置进btnList
        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);

        //设置进菜单类
        menu.setButton(btnList);
        //调用API即可
        try {
            //参数1--menu  ，参数2--是否是个性化定制。如果是个性化菜单栏，需要设置MenuRule
            iService.createMenu(menu, false);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
