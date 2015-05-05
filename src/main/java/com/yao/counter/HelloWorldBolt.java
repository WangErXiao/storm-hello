package com.yao.counter;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yao on 15/5/5.
 */
public class HelloWorldBolt extends BaseRichBolt {

    private final AtomicInteger myCounter=new AtomicInteger(0);

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }


    @Override
    public void execute(Tuple tuple) {
        String test=tuple.getStringByField("sentence");
        if("Hello World".equals(test)){
            myCounter.addAndGet(1);
            System.out.println("found a Hello World ,total count is:"+myCounter.get());
        }

    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
