package com.yao.mq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.yao.mq.utils.DataBaseUtils;
import com.yao.mq.utils.MyDefaultMQPullConsumer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/6.
 */
public class MyMQConsumer implements Serializable {


    private MyDefaultMQPullConsumer mqPullConsumer;

    private String namesrvAddr = "localhost:9876";

    private String topic = "TopicTest1";


    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        DataBaseUtils.offsetTable.put(mq, offset);
    }

    private static long getMessageQuueOffset(MessageQueue mq) {
        Long offset = DataBaseUtils.offsetTable.get(mq);
        if (offset != null) {
            return offset;
        }
        return 0;
    }

    public MyMQConsumer(String groupName) throws MQClientException {
        mqPullConsumer = new MyDefaultMQPullConsumer(groupName);
        mqPullConsumer.setNamesrvAddr(namesrvAddr);
        mqPullConsumer.setInstanceName("Consumer");
        mqPullConsumer.start();

    }

    public List<String> pullMessage() throws MQClientException {
        Set<MessageQueue> messageQueues = mqPullConsumer.fetchSubscribeMessageQueues(topic);

        List<String> result = new ArrayList<>();
        for (MessageQueue mq : messageQueues) {
            System.out.println("Consume from the queue:" + mq);
            try {
                PullResult pullResult = mqPullConsumer.pull(mq, null, getMessageQuueOffset(mq), 2);
                List<MessageExt> list = pullResult.getMsgFoundList();
                if(list==null||list.size()==0){
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                if (list != null && list.size() < 100) {
                    for (MessageExt msg : list) {
                        result.add(new String(msg.getBody()));
                    }
                }
                putMessageQueueOffset(mq, pullResult.getNextBeginOffset());

            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    public void closeConsumer(){
        mqPullConsumer.shutdown();
    }
}
