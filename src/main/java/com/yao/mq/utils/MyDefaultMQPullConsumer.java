package com.yao.mq.utils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;

import java.io.Serializable;

/**
 * Created by yao on 15/5/7.
 */
public class MyDefaultMQPullConsumer extends DefaultMQPullConsumer implements Serializable {
    public MyDefaultMQPullConsumer(String consumerGroup) {
        super(consumerGroup);
    }
}
