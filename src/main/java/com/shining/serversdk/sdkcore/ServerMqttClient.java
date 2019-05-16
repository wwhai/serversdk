package com.shining.serversdk.sdkcore;

import cn.hutool.json.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public final class ServerMqttClient implements IServerMqttClient {
    private String host;
    private int port;
    private MqttClient client;
    private String clientId;
    private String userName;
    private String passWord;

    /**
     * @param host
     * @param port
     * @param clientId
     * @param username
     * @param password
     */
    public ServerMqttClient(String host, int port, String clientId, String username, String password) {

        this.host = host;
        this.port = port;
        this.userName = username;
        this.passWord = password;
        this.clientId = clientId;

    }

    public boolean connect(MqttCallback mqttCallback) {
        try {
            client = new MqttClient("tcp://" + host + ":" + port, clientId, new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            // 设置超时时间
            options.setConnectionTimeout(10);
            // 设置会话心跳时间
            options.setKeepAliveInterval(20);
            client.setCallback(mqttCallback);
            client.connect(options);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    public boolean subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unSubscribe(String topic) {
        try {
            client.unsubscribe(topic);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean publish(String topic, MqttMessage message) {
        try {
            client.publish(topic, message);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean publishJson(String topic, JSONObject message) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.toJSONString(1).getBytes());
        return this.publish(topic, mqttMessage);
    }

    public boolean publishHex(String topic, byte[] bytes) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(bytes);
        return this.publish(topic, mqttMessage);
    }


}
