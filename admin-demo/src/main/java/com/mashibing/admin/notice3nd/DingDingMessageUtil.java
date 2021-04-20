package com.mashibing.admin.notice3nd;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/***********************
 * @Description: 消息发送 <BR>
 * @author: zhao.song
 * @since: 2021/4/20 22:13
 * @version: 1.0
 ***********************/
@Component
public class DingDingMessageUtil {


    @Autowired
    private RestTemplate restTemplate;


    public static String access_token = "039e393840afcdd12b64ff4dfddafbfa2d1198f0521ee653a02fac489726abe4";

    public void sendTextMessage(String msg) {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=" + access_token;
        Message message = new Message();
        message.setMsgtype("text");
        message.setText(new MessageInfo(msg));
        HashMap hashMap = restTemplate.postForObject(url, message, HashMap.class);
        System.out.println(hashMap);
    }


    private void sendTextMessageByConn(String msg) {
        try {
            Message message = new Message();
            message.setMsgtype("text");
            message.setText(new MessageInfo(msg));
            URL url = new URL("https://oapi.dingtalk.com/robot/send?access_token=" + access_token);
            // 建立 http 连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String textMessage = JSONObject.toJSONString(message);
            byte[] data = textMessage.getBytes();
            out.write(data);
            out.flush();
            out.close();
            InputStream in = conn.getInputStream();
            byte[] data1 = new byte[in.available()];
            in.read(data1);
            System.out.println(new String(data1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
