package com.shining.serversdk.sdkcore;


import cn.hutool.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author wwhai
 * 服务器端的SDK
 */
public interface IServerMqttClient {

    /**
     * @param topic
     * @param qos
     * @return
     */
    boolean subscribe(String topic, int qos);

    /**
     * @param topic
     * @return
     */
    boolean unSubscribe(String topic);

    /**
     * @param topic
     * @param message
     * @return
     */
    boolean publish(String topic, MqttMessage message);

    /**
     * @param topic
     * @param message
     * @return
     */
    boolean publishJson(String topic, JSONObject message);

    /**
     * @param topic
     * @param bytes
     * @return
     */
    boolean publishHex(String topic, byte[] bytes);


}
