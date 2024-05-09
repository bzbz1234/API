package com.example.demo.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResponseTimeMonitor {
    // 模拟接口处理时间
    public static void processRequest() {
        try {
            Thread.sleep((long) (Math.random() * 1000)); // 模拟处理时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int qps = 10; // 设定 QPS
        int durationSeconds = 30; // 监控持续时间（秒）

        // 创建固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(qps);

        // 开始监控
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationSeconds * 1000);

        while (System.currentTimeMillis() < endTime) {
            // 模拟 qps 个请求同时发起
            for (int i = 0; i < qps; i++) {
                executor.submit(() -> {
                    long requestStartTime = System.currentTimeMillis();
                    processRequest(); // 处理请求
                    long requestEndTime = System.currentTimeMillis();
                    long responseTime = requestEndTime - requestStartTime;
                    System.out.println("Response time: " + responseTime + " ms");
                });
            }

            try {
                Thread.sleep(1000); // 控制每秒发起一次请求
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 关闭线程池
        executor.shutdown();
    }
}
