package com.shining;

import cn.hutool.json.JSONObject;
import com.shining.serversdk.bean.PublishBean;
import com.shining.serversdk.handler.MessageHandler;
import com.shining.serversdk.handler.PublishHandler;
import com.shining.serversdk.sdkcore.ServerMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Main {
    public static void main(String[] args) {
        ServerMqttClient serverMqttClient = new ServerMqttClient("localhost",
                1883,
                "clientId",
                "username",
                "password");
        boolean isConnect = serverMqttClient.connect(new MessageHandler() {

            public void messageArrived(JSONObject receivedMessage) {

            }


        });
        if (isConnect) {

            /**
             * 订阅
             */
            serverMqttClient.subscribe("test", 2);
            /**
             * 发布 Message
             */
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(new byte[]{70, 85, 67, 75});
            serverMqttClient.publish("test", new MqttMessage());
            /**
             * 发布JSON

             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "1");
            jsonObject.put("msgid", System.currentTimeMillis());
            jsonObject.put("type", "1");
            jsonObject.put("price", "1");

            serverMqttClient.publishJson("test", jsonObject);
            /**
             * 发布Hex
             */
            serverMqttClient.publishHex("test", new byte[]{97, 98, 99, 100});
            /**
             * 使用封装好的Pub Bean
             */
            PublishBean publishBean = new PublishBean();
            publishBean.setTopic("test");
            publishBean.setPayload(jsonObject);
//            publishBean.setDup(true);
//            publishBean.setRetained(true);
//            publishBean.setMutable(true);

            serverMqttClient.publish(publishBean, new PublishHandler() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onException(Exception e) {

                }
            });


        } else {
            System.out.println("连接失败");
        }

    }
}
