package com.yao.mq.producer;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.yao.mq.model.EventModel;
import com.yao.mq.utils.EventType;
import com.yao.mq.utils.SourceType;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/7.
 */
public class MyMQProducerTest {

    @Test
    public void  testSendMessage(){

        try {
            MyMQProducer producer=new MyMQProducer();
            EventModel eventModel1=new EventModel("001", EventType.ONE,new Date(), SourceType.PERSON);
            EventModel eventModel2=new EventModel("002", EventType.TWO,new Date(), SourceType.DOG);
            EventModel eventModel3=new EventModel("003", EventType.THREE,new Date(), SourceType.CAT);
            int count=0;
            while (true){
                producer.produceMessage(eventModel1,eventModel2,eventModel3);
                if(++count>1000){
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            }

            producer.closeProducer();

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

    }
}
