# MqttSDK 使用文档
## 导入Jar包
```xml
<dependency>
    <groupId>com.shining</groupId>
    <artifactId>shinelinker-server-sdk</artifactId>
    <version>0.0.1</version>
</dependency>

```

## 构造器
```
ServerMqttClient serverMqttClient = new ServerMqttClient("localhost",
                1883,
                "clientId",
                "username",
                "password");
```
> 参数分别为：主机，端口，客户端ID，用户名，密码

## 连接
```
boolean isConnect = serverMqttClient.connect(new MqttCallback() {
    public void connectionLost(Throwable throwable) {
        throwable.printStackTrace();

    }

    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("收到消息:" + new String(mqttMessage.getPayload(), "UTF-8"));

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("消息发送完成:" + iMqttDeliveryToken.getMessageId());

    }
});
```

> 连接函数需要传一个回调，用户处理消息在里面完成，返回值为boolean，标志连接成功失败。

## 订阅
```
serverMqttClient.subscribe("topic", 2);

```
> 参数为：topic，QOS

## 发布
```
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
            
```

## 完整Demo
```java
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


```