package com.zkdesign.zookeeper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qiurunze
 **/
@Controller
public class MainControl implements InitializingBean {
    @Value("${zk:112.126.97.242:2181}")
    private String server;
    private ZkClient zkClient;
    private static final String rootPath = "/qiurunze-manger";
    Map<String, OsBean> map = new HashMap<>();

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("items", getCurrentOsBeans());
        return "list";
    }

    private List<OsBean> getCurrentOsBeans() {
        List<OsBean> items = zkClient.getChildren(rootPath).stream()
                .map(p -> rootPath + "/" + p)
                .map(p -> convert(zkClient.readData(p)))
                .collect(Collectors.toList());
        return items;
    }

    private OsBean convert(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, OsBean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        zkClient = new ZkClient(server, 5000, 10000);
        initSubscribeListener();

    }

    // 初始化订阅事件
    public void initSubscribeListener() {
        zkClient.unsubscribeAll();
        // 获取所有子节点
        zkClient.getChildren(rootPath)
                .stream()
                .map(p -> rootPath + "/" + p)// 得出子节点完整路径
                .forEach(p -> {
            zkClient.subscribeDataChanges(p, new DataChanges());// 数据变更的监听
        });
        //  监听子节点，的变更 增加，删除
        zkClient.subscribeChildChanges(rootPath, (parentPath, currentChilds) -> initSubscribeListener());
    }

    // 子节点数据变化
    private class DataChanges implements IZkDataListener {

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            OsBean bean = convert((String) data);
            map.put(dataPath, bean);
            doFilter(bean);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            if (map.containsKey(dataPath)) {
                OsBean bean = map.get(dataPath);
                System.err.println("服务已下线:" + bean);
                map.remove(dataPath);
            }
        }
    }

    // 警告过滤
    private void doFilter(OsBean bean) {
        // cpu 超过10% 报警
        if (bean.getCpu() > 10) {
            System.err.println("CPU 报警..." + bean.getCpu());
        }
    }

}
