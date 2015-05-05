package com.yao.counter;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/5.
 */
public class HelloWorldSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private int referenceRandom;
    private static final int MAX_RANDOM=10;

    public HelloWorldSpout() {
        final Random random=new Random();
        referenceRandom=random.nextInt(MAX_RANDOM);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        outputFieldsDeclarer.declare(new Fields("sentence"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {

        this.collector=spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Random random=new Random();
        int instanceRandom=random.nextInt(MAX_RANDOM);
        if(instanceRandom==referenceRandom){
            collector.emit(new Values("Hello World"));
        }else{
            collector.emit(new Values("Other Random World"));
        }

    }
}
