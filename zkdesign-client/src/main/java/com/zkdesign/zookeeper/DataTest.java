package com.zkdesign.zookeeper;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author 邱润泽 bullock
 */
@Slf4j
public class DataTest {

    ZooKeeper zooKeeper ;

    private static  final String NODE_NAME = "/qiurunze" ;

    @Before
    public void init() throws IOException {
        String conn = "112.126.97.242:2181";
        zooKeeper = new ZooKeeper(conn, 4000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                log.info("i am watch u "+watchedEvent.getPath());
                System.out.println(watchedEvent);

            }
        });
    }


    @Test
    public void getData() throws KeeperException, InterruptedException {

        byte[] data = zooKeeper.getData("/qiurunze",false,null);

        log.info(new String(data));
    }


    @Test
    public void getDataWatch() throws KeeperException, InterruptedException {

        byte[] data = zooKeeper.getData("/qiurunze",true,null);
        log.info(new String(data));
        Thread.sleep(Long.MAX_VALUE);
    }



    //一直监听
    @Test
    public void getDataWatchKeepLive() throws KeeperException, InterruptedException {

        Stat stat =new Stat();
        zooKeeper.getData(NODE_NAME, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    zooKeeper.getData(watchedEvent.getPath(),this,null);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(watchedEvent.getPath());
            }
        },stat);

        log.info(JSON.toJSONString(stat));
        Thread.sleep(Long.MAX_VALUE);

    }


    @Test
    public void getChild() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(NODE_NAME, false);
        children.stream().forEach(System.out::println);
    }


}
