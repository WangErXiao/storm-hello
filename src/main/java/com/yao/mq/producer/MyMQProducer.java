package com.yao.mq.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.*;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.yao.mq.model.EventModel;

import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/6.
 */
public class MyMQProducer {
    private final DefaultMQProducer producer;
    private final String namesrvAddr="localhost:9876";
    public MyMQProducer() throws MQClientException {
        producer=new TransactionMQProducer("ProducerGroupName");
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName("Producer");
        producer.start();
    }

    public void produceMessage(EventModel one,EventModel two ,EventModel three) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 0; i < 10; i++) {
            try {
                {
                    Message msg = new Message("TopicTest1",// topic
                            "ONE",// tag
                            one.getId(),// key消息关键词，多个Key用KEY_SEPARATOR隔开（查询消息使用）
                            (JSON.toJSONString(one)).getBytes());// body
                    producer.send(msg);
                }
                {
                    Message msg = new Message("TopicTest1",// topic
                            "TWO",// tag
                            two.getId(),// key 消息关键词，多个Key用KEY_SEPARATOR隔开（查询消息使用）
                            (JSON.toJSONString(two)).getBytes());// body
                    producer.send(msg);
                }
                {
                    Message msg = new Message("TopicTest1",// topic
                            "THREE",// tag
                            three.getId(),// key
                            (JSON.toJSONString(three)).getBytes());// body
                    producer.send(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }

    }


    public void closeProducer(){
        producer.shutdown();
    }
}
