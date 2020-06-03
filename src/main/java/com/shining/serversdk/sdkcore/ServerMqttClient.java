package com.shining.serversdk.sdkcore;


import cn.hutool.json.JSONObject;
import com.shining.serversdk.bean.PublishBean;
import com.shining.serversdk.handler.MessageHandler;
import com.shining.serversdk.handler.PublishHandler;
import com.shining.serversdk.handler.SubscribeHandler;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public final class ServerMqttClient implements IServerMqttClient {
    private boolean isConnected;
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

    public boolean connect(MessageHandler messageHandler) {
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
            client.setCallback(messageHandler);
            client.connect(options);
            isConnected = true;
            messageHandler.onConnected();
            return true;

        } catch (Exception e) {
            e.printStackTrace();

            isConnected = false;
            return false;
        }


    }


    /**
     * 订阅
     *
     * @param topic
     * @param qos
     * @param subscribeHandler
     * @return
     */
    public boolean subscribe(String topic, int qos, SubscribeHandler subscribeHandler) {
        try {
            client.subscribe(topic, qos);
            subscribeHandler.onSuccess(topic, qos);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            subscribeHandler.onError(e);
            return false;
        }
    }

    /**
     * 取消订阅
     *
     * @param topic
     * @return
     */

    public boolean unSubscribe(String topic) {
        try {
            client.unsubscribe(topic);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 发布
     *
     * @param topic
     * @param message
     * @return
     */
    public boolean publish(String topic, MqttMessage message, PublishHandler publishHandler) {
        try {
            client.publish(topic, message);
            publishHandler.onSuccess();
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            publishHandler.onException(e);
            return false;
        }
    }

    /**
     * 发布JSON
     * @param topic
     * @param message
     * @param publishHandler
     * @return
     */
    public boolean publishJson(String topic, JSONObject message, PublishHandler publishHandler) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.toJSONString(1).getBytes());
        return this.publish(topic, mqttMessage, publishHandler);
    }

    /**
     * 发布16进制
     * @param topic
     * @param bytes
     * @param publishHandler
     * @return
     */
    public boolean publishHex(String topic, byte[] bytes, PublishHandler publishHandler) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(bytes);
        return this.publish(topic, mqttMessage, publishHandler);
    }

    /**
     * 发布消息
     * @param publishBean
     * @param publishHandler
     * @return
     */
    public boolean publish(PublishBean publishBean, PublishHandler publishHandler) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(publishBean.getQos());
        mqttMessage.setRetained(publishBean.isRetained());
        mqttMessage.setPayload(publishBean.getPayload().toString().getBytes());
        try {
            client.publish(publishBean.getTopic(), mqttMessage);
            publishHandler.onSuccess();
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            publishHandler.onException(e);

            return false;
        }
    }

    /**
     * 是否在线
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }

}
