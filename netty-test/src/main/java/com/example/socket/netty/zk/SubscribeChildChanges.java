/**
 * Copyright (C),2018, 萤石
 * FileName: SubscribeChildChanges
 * Author:   caiyaming
 * Date:     2018-12-19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.socket.netty.zk;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description：<br> 监听zk节点变化
 * @author: caiyaming
 * @date: 2018-12-19 16:14
 * @since: V1.0.0
 */
@Slf4j
public class SubscribeChildChanges {
    private static final String TEMP = "/temp";
    private static final String SUPER = "/super";
    private static final String SUPER_C1 = "/super/c1";
    private static final String SUPER_C2 = "/super/c2";

    public static void main(String[] args) {
        ZkClient zkClient = null;
        try {
            zkClient = new ZkClient(new ZkConnection("127.0.0.1:2181"), 30000);
            //监听zk节点变化
            zkClient.subscribeChildChanges(SUPER, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    log.info("parentPath：" + parentPath);
                    log.info("currentChilds：" + currentChilds);
                }
            });

            //监听节点值变化(该接口只会对所监控的path的数据变化，子节点数据发送变化不会被监控到。)
            zkClient.subscribeDataChanges(SUPER_C1, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                    log.info("变更节点为：" + s + "，变更数据为：" + o);
                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    log.info("删除的节点为：" + s);
                }
            });
            log.info("监听启动--------------");
            TimeUnit.SECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            zkClient.close();
        }
    }
}
