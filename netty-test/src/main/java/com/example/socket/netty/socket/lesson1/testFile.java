/**
 * Copyright (C),2018, 萤石
 * FileName: testFile
 * Author:   caiyaming
 * Date:     2018-12-10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.socket.netty.socket.lesson1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @description：<br>
 *
 * @author: caiyaming
 * @date: 2018-12-10 17:07
 * @since: V1.0.0
 */
public class testFile {
    public static void main(String[] args) {
        try {
            FileInputStream fis=new FileInputStream("netty-service/netty-test/target/classes/1.txt");
            InputStreamReader isr=new InputStreamReader(fis,"utf8");
            BufferedReader br=new BufferedReader(isr);
            String line;
            while((line=br.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
