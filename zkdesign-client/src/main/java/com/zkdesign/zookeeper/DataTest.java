package com.zkdesign.zookeeper;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
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
        zooKeeper = new ZooKeeper(conn, 100000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
//                log.info("i am watch u "+watchedEvent.getPath());
                System.out.println(watchedEvent);

            }
        });
    }


    @Test
    public void getData() throws KeeperException, InterruptedException {

        byte[] data = zooKeeper.getData("/qiurunze",false,null);
        System.out.println(new String(data));

//        log.info(new String(data));
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

    @Test
    public void getData4() throws KeeperException, InterruptedException {
        zooKeeper.getData("/qiurunze", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                log.info("stat:{}",JSON.toJSON(stat));
            }
        }, "");
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void createData() throws KeeperException, InterruptedException {
        List<ACL> list = new ArrayList<>();
        int perm = ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ;//cdwra
        ACL acl = new ACL(perm, new Id("world", "anyone"));
        ACL acl2 = new ACL(perm, new Id("ip", "192.168.0.149"));
        ACL acl3 = new ACL(perm, new Id("ip", "127.0.0.1"));
        list.add(acl);
        list.add(acl2);
        list.add(acl3);
        zooKeeper.create("/qiurunze/gekkq", "hello".getBytes(), list, CreateMode.PERSISTENT);
    }

    @Test
    public void getChild2() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/qiurunze", event -> {
            System.out.println(event.getPath());
            try {
                zooKeeper.getChildren(event.getPath(), false);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        children.stream().forEach(System.out::println);
        Thread.sleep(Long.MAX_VALUE);
    }

}
