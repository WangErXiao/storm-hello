package com.yao.mq.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.*;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yao.mq.model.EventModel;

import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/6.
 */
public class MyMQProducer {
    final TransactionMQProducer producer;
    final String namesrvAddr="localhost:9999";
    public MyMQProducer() throws MQClientException {
        producer=new TransactionMQProducer("ProducerGroupName");
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName("Producer");
        producer.start();
        producer.setTransactionCheckListener(new TransactionCheckListener() {
            public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
                System.out.println("checkLocalTransactionState --" + new String(msg.getBody()));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

    }

    public void produceMessage(EventModel one,EventModel two ,EventModel three) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            try {
                {
                    Message msg = new Message("TopicTest1",// topic
                            "ONE",// tag
                            one.getId(),// key消息关键词，多个Key用KEY_SEPARATOR隔开（查询消息使用）
                            (JSON.toJSONString(one)).getBytes());// body
                    SendResult sendResult = producer.sendMessageInTransaction(msg,new MyMQProducer().new MyTransactionExecuter(),"$$$");
                            System.out.println(sendResult);
                }

                {
                    Message msg = new Message("TopicTest2",// topic
                            "TWO",// tag
                            two.getId(),// key 消息关键词，多个Key用KEY_SEPARATOR隔开（查询消息使用）
                            (JSON.toJSONString(two)).getBytes());// body
                    SendResult sendResult = producer.sendMessageInTransaction(msg,new MyMQProducer().new MyTransactionExecuter(),"$$$");
                            System.out.println(sendResult);
                }

                {
                    Message msg = new Message("TopicTest3",// topic
                            "THREE",// tag
                            three.getId(),// key
                            (JSON.toJSONString(three)).getBytes());// body
                    SendResult sendResult = producer.sendMessageInTransaction(msg,new MyMQProducer().new MyTransactionExecuter(),"$$$");
                            System.out.println(sendResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }

    }

    //执行本地事务，由客户端回调
    public class MyTransactionExecuter implements LocalTransactionExecuter {
        public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
            System.out.println("executeLocalTransactionBranch--msg="+new String(msg.getBody()));
            System.out.println("executeLocalTransactionBranch--arg="+arg);
            return LocalTransactionState.COMMIT_MESSAGE;
        }

    }
    public void closeProducer(){
        producer.shutdown();
    }
}
