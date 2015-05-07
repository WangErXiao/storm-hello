package com.yao.mq.utils;

import com.alibaba.rocketmq.common.message.MessageQueue;
import com.yao.mq.model.StaticModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yao on 15/5/7.
 */
public class DataBaseUtils {
    public static final Map<EventType ,StaticModel> staticMap=new ConcurrentHashMap<>();

    public static final Map<MessageQueue, Long> offsetTable = new ConcurrentHashMap<>();


}
