# MqttSDK 使用文档
## 更新日志【2019-07-31】
```
1.API变成事件驱动形式，使用和捕捉信息更加便捷
2.版本号统一改变为final，永远只有一个版本
```
## 导入Jar包
```xml
<dependency>
    <groupId>com.shining</groupId>
    <artifactId>shinelinker-server-sdk</artifactId>
    <version>final</version>
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

## 连接
```
        serverMqttClient.connect(new MessageHandler() {
        
            //收到消息
            public void messageArrived(JSONObject receivedMessage) {

            }
            //出错
            public void onError(Throwable throwable) {

            }
           //发送完成
            public void onComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
            //连接成功
            public void onConnected() {
                
            }


        });
```

> 连接函数需要传一个回调，用户处理消息在里面完成，返回值为boolean，标志连接成功失败。

## 订阅
```
//QOS对应了MQTT的：0 ，1 ，2【注意：阿里云的Mqtt不支持2】
serverMqttClient.subscribe("topic", 2);

```

## 发布
```
        /**
         * 发布之前一般判断一下判断是否在线
         */
        if (serverMqttClient.isConnected()) {

            /**
             * 订阅
             */
            serverMqttClient.subscribe("test", 2, new SubscribeHandler() {
                @Override
                public void onSuccess(String topic, int qos) {

                }

                @Override
                public void onError(Exception e) {

                }
            });
            /**
             * 发布 Message
             */
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(new byte[]{70, 85, 67, 75});
            serverMqttClient.publish("test", new MqttMessage(), new PublishHandler() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onException(Exception e) {

                }
            });
            /**
             * 发布JSON

             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "1");
            jsonObject.put("msgid", System.currentTimeMillis());
            jsonObject.put("type", "1");
            jsonObject.put("price", "1");

            serverMqttClient.publishJson("test", jsonObject, new PublishHandler() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onException(Exception e) {

                }
            });
            /**
             * 发布Hex
             */
            serverMqttClient.publishHex("test", new byte[]{97, 98, 99, 100}, new PublishHandler() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onException(Exception e) {

                }
            });
            /**
             * 使用【SDK内置】封装好的Pub Bean
             */
            PublishBean publishBean = new PublishBean();
            publishBean.setTopic("test");
            publishBean.setPayload(jsonObject);
//          publishBean.setDup(true);
//          publishBean.setRetained(true);
//          publishBean.setMutable(true);

            serverMqttClient.publish(publishBean, new PublishHandler() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onException(Exception e) {

                }
            });


        } 

    }      
```

## 完整Demo
```java
package com.shining;
import cn.hutool.json.JSONObject;
import com.shining.serversdk.bean.PublishBean;
import com.shining.serversdk.handler.MessageHandler;
import com.shining.serversdk.handler.PublishHandler;
import com.shining.serversdk.handler.SubscribeHandler;
import com.shining.serversdk.sdkcore.ServerMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
public class Main {
    public static void main(String[] args) {
        ServerMqttClient serverMqttClient = new ServerMqttClient("localhost",
                1883,
                "clientId",
                "username",
                "password");
        serverMqttClient.connect(new MessageHandler() {
            public void messageArrived(JSONObject receivedMessage) {
            }
            public void onError(Throwable throwable) {
            }
            public void onComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        /**
         * 判断是否在线
         */
        if (serverMqttClient.isConnected()) {
            /**
             * 订阅
             */
            serverMqttClient.subscribe("test", 2, new SubscribeHandler() {
                @Override
                public void onSuccess(String topic, int qos) {
                }
                @Override
                public void onError(Exception e) {
                }
            });
            /**
             * 发布 Message
             */
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(new byte[]{70, 85, 67, 75});
            serverMqttClient.publish("test", new MqttMessage(), new PublishHandler() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onException(Exception e) {
                }
            });
            /**
             * 发布JSON
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "1");
            jsonObject.put("msgid", System.currentTimeMillis());
            jsonObject.put("type", "1");
            jsonObject.put("price", "1");
            serverMqttClient.publishJson("test", jsonObject, new PublishHandler() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onException(Exception e) {
                }
            });
            /**
             * 发布Hex
             */
            serverMqttClient.publishHex("test", new byte[]{97, 98, 99, 100}, new PublishHandler() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onException(Exception e) {
                }
            });
            /**
             * 使用封装好的Pub Bean
             */
            PublishBean publishBean = new PublishBean();
            publishBean.setTopic("test");
            publishBean.setPayload(jsonObject);
            publishBean.setDup(true);
            publishBean.setRetained(true);
            publishBean.setMutable(true);
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

```