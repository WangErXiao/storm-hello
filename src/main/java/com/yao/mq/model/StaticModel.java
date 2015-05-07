package com.yao.mq.model;

import com.yao.mq.utils.EventType;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yao on 15/5/7.
 */
public class StaticModel {
    private AtomicInteger totalNum=new AtomicInteger(0);
    private EventType eventType;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public AtomicInteger getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(AtomicInteger totalNum) {
        this.totalNum = totalNum;
    }
}
