package com.shining;

import cn.hutool.json.JSONObject;
import com.shining.serversdk.handler.MessageHandler;
import com.shining.serversdk.sdkcore.ServerMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Main {
    public static void main(String[] args) {
        ServerMqttClient serverMqttClient = new ServerMqttClient("39.96.58.211",
                1883,
                "clientId",
                "username",
                "password");
        boolean isConnect = serverMqttClient.connect(new MessageHandler() {
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                super.messageArrived(topic, mqttMessage);
                System.out.println(mqttMessage);
            }
        });
        if (isConnect) {

            /**
             * 订阅
             */
            serverMqttClient.subscribe("zyzs", 2);
            /**
             * 发布 Message
             */
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(new byte[]{70, 85, 67, 75});
            //serverMqttClient.publish("zyzs", new MqttMessage());
            /**
             * 发布JSON
             *
             Madison 2019/5/16 10:52:04
             {
             "action": "1",
             "msgid": "20190221095350622453353",
             "type": "1",
             "price": "1"
             }
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "1");
            jsonObject.put("msgid", System.currentTimeMillis());
            jsonObject.put("type", "1");
            jsonObject.put("price", "1");

            serverMqttClient.publishJson("zyzs", jsonObject);
            /**
             * 发布Hex
             */
           // serverMqttClient.publishHex("zyzs", new byte[]{97, 98, 99, 100});
        }else {
            System.out.println("连接失败");
        }

    }
}
