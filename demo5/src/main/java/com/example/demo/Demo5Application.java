package com.example.demo;

import com.example.demo.monitor.RequestMonitor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@SpringBootApplication
@MapperScan(basePackages={"com.example.demo.repository"})
@MapperScan(basePackages={"com.example.demo.config"})
@EnableScheduling

public class Demo5Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo5Application.class, args);
    }

    @Bean
    public RequestMonitor requestMonitor() {
        return new RequestMonitor();
    }

    @Component
    public static class RequestInterceptor implements HandlerInterceptor {
        @Autowired
        private RequestMonitor requestMonitor;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 在请求到达时增加请求计数
            requestMonitor.incrementRequestCount();
            return true;
        }
    }

    @Component
    public static class RequestMonitorScheduler {

        @Autowired
        private RequestMonitor requestMonitor;

        // 每秒定时输出请求量
        @Scheduled(fixedRate = 1000)
        public void monitorRequestsPerSecond() {
            long count = requestMonitor.getRequestCountPerSecond();
            System.out.println("Requests per second: " + count);
        }
    }
}


