package com.yao.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.yao.mq.model.EventModel;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/7.
 */
public class MyMQConsumerTest {
    @Test
    public void testPullMessage(){
        try {

            MyMQConsumer myMQConsumer=new MyMQConsumer("groupname");
            for (int i=0;i<1000;i++){
                List<String> result=myMQConsumer.pullMessage();
                for (String msg:result){
                    System.out.println(msg);
                    EventModel eventModel=JSON.parseObject(msg, EventModel.class);
                    System.out.println(eventModel.getId());
                }
                TimeUnit.SECONDS.sleep(2);
            }

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
