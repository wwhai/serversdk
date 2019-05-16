package com.shining;

import cn.hutool.json.JSONObject;
import com.shining.serversdk.handler.MessageHandler;
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
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                super.messageArrived(topic, mqttMessage);
            }
        });
        if (isConnect) {

            /**
             * 订阅
             */
            serverMqttClient.subscribe("topic", 2);
            /**
             * 发布 Message
             */
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(new byte[]{70, 85, 67, 75});
            serverMqttClient.publish("topic", new MqttMessage());
            /**
             * 发布JSON
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "wwhai");
            serverMqttClient.publishJson("topic", jsonObject);
            /**
             * 发布Hex
             */
            serverMqttClient.publishHex("topic", new byte[]{97, 98, 99, 100});
        }

    }
}
