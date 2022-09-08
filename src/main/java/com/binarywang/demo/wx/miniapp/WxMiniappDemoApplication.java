package com.binarywang.demo.wx.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import com.google.gson.JsonObject;
import me.chanjar.weixin.common.enums.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.json.GsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

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
        List<WxMaTemplateData> dataList = generateTemplateDataList("不良品", "不良品数异常提醒");
        message.setData(dataList);
        message.setMpTemplateMsg(true);
//        message.setEmphasisKeyword("杭州市");    // 小程序模板放大关键词（可不传）
//        message.setFormId("");  // 小程序模板消息formid（可不传）
//        message.setPage("pages/login/login");    // 小程序页面路径（可不传）
//        message.setTemplateId("6F65YdXVeiBCjupB0VqN_D__tdxZtdxSLqX45gVWRRw");  // 小程序模板ID
//        message.setUrl("https://www.baidu.com"); // 公众号模板消息所要跳转的url

        message.setToUser("oMZs_53u4k6-2JCBbBe3Ju3C_yPk");  // todo: 用户openid，可以是小程序的openid，也可以是mp_template_msg.appid对应的公众号的openid
        message.setAppid("wxcb7b5fafd106e07d"); // 公众号appid
        message.setTemplateId("Ss_NiwSN_igfHqOExH3jGwoS2QVBUn3gXVq87B_227A");  // todo: 设置消息模板id
        WxMaUniformMessage.MiniProgram miniProgram = new WxMaUniformMessage.MiniProgram();
        miniProgram.setAppid("wx499352f10b9b9da2"); // todo: 小程序appid
        miniProgram.setPagePath("/pagesDetail/workReport/workReportEdit/workReportEdit?workReportId=1639458638607360&from=taskList");    // todo: 设置跳转的小程序页面路径
        miniProgram.setUsePath(false);  // todo: 不知道干嘛的？
        miniProgram.setUsePagePath(true);   // todo: 不知道干嘛的？
        message.setMiniProgram(miniProgram);
        this.maService.getMsgService().sendUniformMsg(message);
        this.sendUniformMsg(message);
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

    @GetMapping("/showNow")
    public String showNow() {
        DateTimeFormatter pierFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String nowStr = LocalDateTime.now().format(pierFormater);
        return nowStr;
    }

    private List<WxMaTemplateData> generateTemplateDataList(String keyword1, String keyword3) {
        List<WxMaTemplateData> msgDataList = new ArrayList<>();
        WxMaTemplateData data1 = new WxMaTemplateData();
        data1.setName("first");
        data1.setValue("异常状态提醒");
        msgDataList.add(data1);
        WxMaTemplateData data2 = new WxMaTemplateData();
        data2.setName("keyword1");
        data2.setValue(keyword1);
        msgDataList.add(data2);
        WxMaTemplateData data3 = new WxMaTemplateData();
        data3.setName("keyword2");
        data3.setValue(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        msgDataList.add(data3);
        WxMaTemplateData data4 = new WxMaTemplateData();
        data4.setName("keyword3");
        data4.setValue(keyword3);
        msgDataList.add(data4);
        WxMaTemplateData data5 = new WxMaTemplateData();
        data5.setName("remark");
        data5.setValue("");
        msgDataList.add(data5);
        return msgDataList;
    }

    private void sendUniformMsg(WxMaUniformMessage uniformMessage) throws WxErrorException {
        String requestJson = uniformMessage.toJson().replace("pagePath", "pagepath");
        String responseContent = this.maService.post("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send", requestJson);
        JsonObject jsonObject = GsonParser.parse(responseContent);
        if (jsonObject.get("errcode").getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
        }
    }
}
