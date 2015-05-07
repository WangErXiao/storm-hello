package com.yao.mq.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.alibaba.fastjson.JSON;
import com.yao.mq.model.EventModel;
import com.yao.mq.model.StaticModel;
import com.yao.mq.utils.DataBaseUtils;
import com.yao.mq.utils.EventType;

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
        staticEvent(eventModel);
    }
    private void staticEvent(EventModel eventModel){
        if(DataBaseUtils.staticMap.get(eventModel.getEventType())!=null){
            StaticModel staticModel=DataBaseUtils.staticMap.get(eventModel.getEventType());
            staticModel.getTotalNum().incrementAndGet();
        }else{
            StaticModel staticModel=new StaticModel();
            staticModel.getTotalNum().incrementAndGet();
            staticModel.setEventType(eventModel.getEventType());
            DataBaseUtils.staticMap.put(staticModel.getEventType(),staticModel);
        }
        showStatic();
    }
    private void showStatic(){
        for (Map.Entry<EventType,StaticModel> entry:DataBaseUtils.staticMap.entrySet()){
            System.out.println("++++++++++++:"+entry.getKey()+"  value:"+entry.getValue().getTotalNum());
        }
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {


    }
}
