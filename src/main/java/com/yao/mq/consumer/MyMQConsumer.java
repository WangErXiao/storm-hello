package com.yao.mq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yao on 15/5/6.
 */
public class MyMQConsumer {

    private static final Map<MessageQueue,Long> offsetTable=new ConcurrentHashMap<>();

    private DefaultMQPullConsumer mqPullConsumer;

    private String namesrvAddr="localhost:9999";

    private String topic="TopicTest";


    private static void putMessageQueueOffset(MessageQueue mq,long offset){
        offsetTable.put(mq,offset);
    }

    private static long getMessageQuueOffset(MessageQueue mq){
        Long offset=offsetTable.get(mq);
        if(offset!=null){
            return offset;
        }
        return 0;
    }

    public MyMQConsumer() throws MQClientException {
        mqPullConsumer = new DefaultMQPullConsumer("ConsumerGroupName");
        mqPullConsumer.setNamesrvAddr(namesrvAddr);
        mqPullConsumer.setInstanceName("Consumer");
        mqPullConsumer.start();

    }

    public List<String> pullMessage() throws MQClientException {
        Set<MessageQueue> messageQueues=mqPullConsumer.fetchSubscribeMessageQueues(topic);

        List<String> result=new ArrayList<>();
        for (MessageQueue mq:messageQueues) {
            System.out.println("Consume from the queue:" + mq);
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult = mqPullConsumer.pullBlockIfNotFound(mq, null, getMessageQuueOffset(mq), 32);
                    List<MessageExt> list = pullResult.getMsgFoundList();
                    if (list != null && list.size() < 100) {
                        for (MessageExt msg : list) {
                            result.add(new String(msg.getBody()));
                        }
                    }
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (RemotingException e) {
                    e.printStackTrace();
                } catch (MQBrokerException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public void closeConsumer(){
        mqPullConsumer.shutdown();
    }
}
