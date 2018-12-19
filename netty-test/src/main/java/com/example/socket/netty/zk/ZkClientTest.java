/**
 * Copyright (C),2018, 萤石
 * FileName: ZkClientTest
 * Author:   caiyaming
 * Date:     2018-12-19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.socket.netty.zk;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description：<br>
 *
 * @author: caiyaming
 * @date: 2018-12-19 15:32
 * @since: V1.0.0
 */
@Slf4j
public class ZkClientTest {

    private static final String TEMP = "/temp";
    private static final String SUPER = "/super";
    private static final String SUPER_C1 = "/super/c1";
    private static final String SUPER_C2 = "/super/c2";

    public static void main1(String[] args) {
        ZkClient zkClient = null;
        try {
            zkClient = new ZkClient(new ZkConnection("127.0.0.1:2181"), 30000);

            if (!zkClient.exists(TEMP)) {//判断指定节点是否存在
                //创建临时节点,并且赋值,会话失效后删除
                zkClient.createEphemeral("/temp","112");
            }
            //读取节点值 (读取节点值前,先判断节点是否存在)
            String data = zkClient.readData(TEMP);
            log.info("读取节点"+TEMP+"/值: " + data);
//#########################################################################################################
            if (!zkClient.exists(SUPER_C1)) {//判断指定节点是否存在
                //创建持久化节点，true表示如果父节点不存在则创建父节点
                zkClient.createPersistent(SUPER_C1, true);
                //3.更新和判断节点是否存在
                zkClient.writeData(SUPER_C1, "新内容"); //修改指定节点的值
            }
            //读取节点值
            String data2 = zkClient.readData(SUPER_C1);
            log.info("读取节点"+SUPER_C1+"/值: " + data2);
//#########################################################################################################
            if (!zkClient.exists(SUPER_C2)) {//判断指定节点是否存在
                //创建并设置节点的值 (需保证根节点存在)
                zkClient.createPersistent(SUPER_C2, "1234");
                //3.更新和判断节点是否存在
                zkClient.writeData(SUPER_C2, "新内容"); //修改指定节点的值
            }
            //读取节点值
            String data1 = zkClient.readData(SUPER_C2);
            log.info("读取节点"+SUPER_C2+"/值: " + data1);

//#########################################################################################################
            //修改指定节点的值(先判断节点是否存在)
            zkClient.writeData(SUPER_C1, "新内容2");
            //读取节点值
            String data3 = zkClient.readData(SUPER_C1);
            log.info("读取节点"+SUPER_C1+"/值: " + data3);
//#########################################################################################################
            List<String> children = zkClient.getChildren(SUPER);
            for(String child : children) {
                log.info( SUPER + "下子节点"+ child);
                String childPath = SUPER+ "/" + child;
                String data4 = zkClient.readData(childPath); //读取指定节点的值
                log.info("读取节点"+childPath+"/值: " + data4);
            }
//#########################################################################################################
            //删除节点(只能删除最底层节点)
            zkClient.delete(TEMP);
            //递归删除，如果该节点下有子节点，会把子节点也删除
            zkClient.deleteRecursive(SUPER);
        } finally {
            zkClient.close();
        }

    }


    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(new ZkConnection("127.0.0.1:2181"), 30000);
        if (!zkClient.exists(SUPER_C1)) {//判断指定节点是否存在
            //创建持久化节点，true表示如果父节点不存在则创建父节点
            zkClient.createPersistent(SUPER_C1, true);
            //3.更新和判断节点是否存在
            zkClient.writeData(SUPER_C1, "新内容"); //修改指定节点的值
        }
        TimeUnit.SECONDS.sleep(3);
        //修改指定节点的值(先判断节点是否存在)
        zkClient.writeData(SUPER_C1, "新内容2");
        TimeUnit.SECONDS.sleep(3);

        zkClient.deleteRecursive(SUPER);

        zkClient.close();

    }
}
