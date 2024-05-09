package com.example.demo.monitor;

import java.util.concurrent.atomic.AtomicLong;

public class RequestMonitor {
    private static final long MONITOR_INTERVAL = 1000; // 监控间隔，单位毫秒
    private AtomicLong requestCount = new AtomicLong(0);
    private long lastMonitorTime = System.currentTimeMillis();

    // 请求计数加一
    public void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    // 获取最近一秒的请求数量
    public long getRequestCountPerSecond() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMonitorTime >= MONITOR_INTERVAL) {
            long count = requestCount.getAndSet(0);
            lastMonitorTime = currentTime;
            return count;
        } else {
            return 0;
        }
    }

    // 示例：定时打印每秒请求量
    public void startMonitoring() {
        Thread monitorThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(MONITOR_INTERVAL);
                    long count = getRequestCountPerSecond();
                    System.out.println("Requests per second: " + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        monitorThread.start();
    }

    // 示例：模拟请求，每次调用增加请求计数
    public void simulateRequest() {
        incrementRequestCount();
    }

    // 示例：停止监控
    public void stopMonitoring() {
        // 停止监控线程或进行清理操作
    }

    public static void main(String[] args) {
        RequestMonitor requestMonitor = new RequestMonitor();
        requestMonitor.startMonitoring();

        // 模拟请求
        for (int i = 0; i < 1000; i++) {
            requestMonitor.simulateRequest();
            try {
                Thread.sleep(10); // 模拟请求间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
