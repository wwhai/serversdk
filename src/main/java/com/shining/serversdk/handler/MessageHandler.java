package com.shining.serversdk.handler;

import cn.hutool.json.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 消息处理器
 */
public abstract class MessageHandler implements MqttCallback {
    /**
     * 异常处理
     *
     * @param throwable
     */
    public void connectionLost(Throwable throwable) {
        throwable.printStackTrace();
        onError(throwable);

    }

    /**
     * @param topic       mqttTopic
     * @param mqttMessage Message
     * @throws Exception
     */
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic", topic);
        jsonObject.put("payload", new String(mqttMessage.getPayload(), "UTF-8"));
        jsonObject.put("retain", mqttMessage.isRetained());
        jsonObject.put("dup", mqttMessage.isDuplicate());
        jsonObject.put("messageId", mqttMessage.getId());

        messageArrived(jsonObject);
    }

    /**
     * 后续步骤
     *
     * @param iMqttDeliveryToken MessageID管理类
     */

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        onComplete(iMqttDeliveryToken);
    }

    /**
     * 消息到达封装JSON
     */
    public abstract void messageArrived(JSONObject receivedMessage);

    /**
     * 出错处理
     */
    public abstract void onError(Throwable throwable);

    /**
     *
     */
    public abstract void onComplete(IMqttDeliveryToken iMqttDeliveryToken);

    /**
     *
     */
    public abstract void onConnected();
}
