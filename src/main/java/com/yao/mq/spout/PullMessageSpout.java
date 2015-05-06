package com.yao.mq.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.yao.mq.consumer.MyMQConsumer;

import java.util.List;
import java.util.Map;

/**
 * Created by yao on 15/5/6.
 */
public class PullMessageSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private MyMQConsumer myMQConsumer;


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("msg"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector=spoutOutputCollector;
        try {
            myMQConsumer=new MyMQConsumer();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
        try {
            List<String> msgs=myMQConsumer.pullMessage();
            for (String msg:msgs){
                collector.emit(new Values(msg));
            }

        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
