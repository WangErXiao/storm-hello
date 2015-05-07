package com.yao.mq.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.yao.mq.bolt.ParseMsgBolt;
import com.yao.mq.consumer.MyMQConsumer;
import com.yao.mq.spout.PullMessageSpout;
import com.yao.mq.utils.CONSTANTS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/7.
 */
public class TopologyMQ {

    public static void main(String[]args) throws InterruptedException, MQClientException {
        TopologyBuilder builder=new TopologyBuilder();
        builder.setSpout("pullMsgSpout",new PullMessageSpout(),1);
        builder.setBolt("countBolt",new ParseMsgBolt(),3).shuffleGrouping("pullMsgSpout");
        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_WORKERS, 2);

        LocalCluster cluster=new LocalCluster();
        cluster.submitTopology("test",conf,builder.createTopology());

        TimeUnit.SECONDS.sleep(1000);
        cluster.killTopology("test");
        cluster.shutdown();

    }
}
