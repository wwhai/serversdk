package com.shining.serversdk.handler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 消息处理器
 */
public class MessageHandler implements MqttCallback {
    /**
     * 异常处理
     *
     * @param throwable
     */
    public void connectionLost(Throwable throwable) {

    }

    /**
     * @param topic       mqttTopic
     * @param mqttMessage Message
     * @throws Exception
     */
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

    }

    /**
     * 后续步骤
     *
     * @param iMqttDeliveryToken MessageID管理类
     */

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
