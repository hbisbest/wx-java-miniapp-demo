package com.binarywang.demo.wx.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binary wang
 */
@RestController
@RequestMapping("/")
@SpringBootApplication
public class WxMiniappDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxMiniappDemoApplication.class, args);
    }

    @Autowired
    private WxMaService maService;

    @GetMapping("/test")
    public String test() throws WxErrorException {
        return this.maService.getAccessToken();
    }

    @GetMapping("/testUniformMsg")
    public String testUniformMsg() throws WxErrorException {
        WxMaUniformMessage message = new WxMaUniformMessage();
        List<WxMaTemplateData> dataList = new ArrayList<>();
        WxMaTemplateData data1 = new WxMaTemplateData();
        data1.setName("first");
        data1.setValue("沐马维修厂: 你的送返修任务已超过48小时未处理");
        data1.setColor("#0000FF");
        dataList.add(data1);
        WxMaTemplateData data2 = new WxMaTemplateData();
        data2.setName("keyword1");
        data2.setValue("浙A88888");
        dataList.add(data2);
        WxMaTemplateData data3 = new WxMaTemplateData();
        data3.setName("keyword2");
        data3.setValue("布加迪");
        dataList.add(data3);
        WxMaTemplateData data4 = new WxMaTemplateData();
        data4.setName("keyword3");
        data4.setValue("浙江省杭州市余杭区良渚街道大陆工业区");
        dataList.add(data4);
        WxMaTemplateData data5 = new WxMaTemplateData();
        data5.setName("keyword4");
        data5.setValue("送修，需要拖车");
        dataList.add(data5);
        WxMaTemplateData data6 = new WxMaTemplateData();
        data6.setName("remark");
        data6.setValue("请及时跟进处理！");
        dataList.add(data6);
        message.setData(dataList);
        message.setMpTemplateMsg(true);
//        message.setEmphasisKeyword("杭州市");    // 小程序模板放大关键词（可不传）
//        message.setFormId("");  // 小程序模板消息formid（可不传）
//        message.setPage("pages/login/login");    // 小程序页面路径（可不传）
//        message.setTemplateId("6F65YdXVeiBCjupB0VqN_D__tdxZtdxSLqX45gVWRRw");  // 小程序模板ID
//        message.setUrl("https://www.baidu.com"); // 公众号模板消息所要跳转的url

        message.setToUser("oMZs_53u4k6-2JCBbBe3Ju3C_yPk");  // todo: 用户openid，可以是小程序的openid，也可以是mp_template_msg.appid对应的公众号的openid
        message.setAppid("wxcb7b5fafd106e07d"); // 公众号appid
        message.setTemplateId("XTZCWDUvQTjuE6MCMIM-FgNu9_gnnOdknKN55tomDxw");  // todo: 设置消息模板id
        WxMaUniformMessage.MiniProgram miniProgram = new WxMaUniformMessage.MiniProgram();
        miniProgram.setAppid("wx499352f10b9b9da2"); // todo: 小程序appid
        miniProgram.setPagePath("pages/login/login");    // todo: 设置跳转的小程序页面路径
        miniProgram.setUsePath(false);  // todo: 不知道干嘛的？
        miniProgram.setUsePagePath(true);   // todo: 不知道干嘛的？
        message.setMiniProgram(miniProgram);
        this.maService.getMsgService().sendUniformMsg(message);
        return "Success";
    }

    @GetMapping("/testSubscribeMsg")
    public String testSubscribeMsg() throws WxErrorException {
        WxMaSubscribeMessage message = new WxMaSubscribeMessage();
        message.setTemplateId("6F65YdXVeiBCjupB0VqN_D__tdxZtdxSLqX45gVWRRw");
        WxMaSubscribeMessage.MsgData data1 = new WxMaSubscribeMessage.MsgData();
        data1.setName("thing1");
        data1.setValue("皮肤体验项目");
        WxMaSubscribeMessage.MsgData data2 = new WxMaSubscribeMessage.MsgData();
        data2.setName("time2");
        data2.setValue("2022-07-01");
        WxMaSubscribeMessage.MsgData data3 = new WxMaSubscribeMessage.MsgData();
        data3.setName("thing3");
        data3.setValue("浙江省杭州市西湖区解放路1108号");
        List<WxMaSubscribeMessage.MsgData> dataList = new ArrayList<>();
        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        message.setData(dataList);
        message.setPage("pages/login/login");
        message.setToUser("o_mZR5DntGqmyOm2HRaaM3oElTzE");
        message.setLang("zh_CN");
        message.setMiniprogramState("formal");
        String json = message.toJson();
        maService.getMsgService().sendSubscribeMsg(message);
//        maService.getSubscribeService().sendSubscribeMsg(message);
        return "Success";
    }

    @GetMapping("/testLogin")
    public String testLogin(String code) throws WxErrorException {
        WxMaJscode2SessionResult result = maService.getUserService().getSessionInfo(code);
        return "openid:"+result.getOpenid();
    }
}
