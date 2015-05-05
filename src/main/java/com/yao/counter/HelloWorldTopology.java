package com.yao.counter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by yao on 15/5/5.
 */
public class HelloWorldTopology{
    public static void main(String[]args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
        TopologyBuilder builder=new TopologyBuilder();
        builder.setSpout("randomHelloWorld", new HelloWorldSpout(), 10);
        builder.setBolt("helloWorldBolt",new HelloWorldBolt(),2).shuffleGrouping("randomHelloWorld");

        Config config=new Config();
        config.setDebug(true);

        if(args!=null&&args.length>0){
            config.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0],config,builder.createTopology());
        }else{
            LocalCluster cluster=new LocalCluster();
            cluster.submitTopology("test",config,builder.createTopology());
            TimeUnit.SECONDS.sleep(1000);
            cluster.killTopology("test");
            cluster.shutdown();
        }

    }
}
