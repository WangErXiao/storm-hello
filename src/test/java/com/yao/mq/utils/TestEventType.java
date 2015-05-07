package com.yao.mq.utils;

import org.junit.Test;

/**
 * Created by yao on 15/5/7.
 */
public class TestEventType {
    @Test
    public void testEnum(){

        EventType eventType=EventType.ONE;
        EventType eventType1=EventType.ONE;
        System.out.println(eventType==eventType1);
    }
}
