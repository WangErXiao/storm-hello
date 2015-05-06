package com.yao.mq.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.alibaba.fastjson.JSON;
import com.yao.mq.model.EventModel;

import java.util.Map;

/**
 * Created by yao on 15/5/6.
 */
public class ParseMsgBolt extends BaseRichBolt {


    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {
        String msg=tuple.getStringByField("msg");
        EventModel eventModel=JSON.parseObject(msg, EventModel.class);



    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {


    }
}
