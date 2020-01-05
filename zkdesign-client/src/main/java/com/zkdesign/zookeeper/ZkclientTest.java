package com.zkdesign.zookeeper;

import com.alibaba.dubbo.remoting.zookeeper.ZookeeperClient;
import com.alibaba.dubbo.remoting.zookeeper.zkclient.ZkclientZookeeperClient;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;


/*** @author 邱润泽 bulloc*/
@Slf4j
public class ZkclientTest {
    ZkClient zkClient;
    @Before
    public void init() {
         zkClient = new ZkClient("112.126.97.242:2181", 5000, 5000);
    }




    @Test
    public void createTest() throws InterruptedException {

        zkClient.subscribeDataChanges("/qiurunze", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                log.info(s);
                log.info(JSON.toJSONString(o));
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                log.info(s);
            }
        });

        Thread.sleep(100000);
    }
}
