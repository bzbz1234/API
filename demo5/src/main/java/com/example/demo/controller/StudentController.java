package com.example.demo.controller;

import com.example.demo.monitor.QpsLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Students;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.UUID;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private QpsLimiter qpsLimiter;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class InvalidDateRangeException extends RuntimeException {
        public InvalidDateRangeException(String message) {
            super(message);
        }
    }

    @PostMapping("/birth")
    public Page<Students> find(@RequestParam("startBirth") String start, @RequestParam("endBirth") String end, @RequestParam(defaultValue = "1")Integer pageIndex, @RequestParam(defaultValue = "15")Integer pageSize){
        if (!qpsLimiter.allowRequest()) {
            return null;
        }
        String logID = UUID.randomUUID().toString();
        long requestStartTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        } catch (DateTimeParseException e) {
            // 抛出自定义异常，表示日期格式不正确
            throw new InvalidDateRangeException("Invalid date format. Please use YYYY-MM-DD.");
        }

        // 检查起始日期和终止日期的顺序
        if (startDate.isAfter(endDate)) {
            // 抛出自定义异常，表示起始日期比终止日期晚
            throw new InvalidDateRangeException("Start date cannot be after end date.");
        }
        Page<Students> studentsPage=studentService.findStudentByBirthBetween(startDate,endDate,pageIndex,pageSize);
        long requestEndTime = System.currentTimeMillis(); // 记录请求结束时间
        long responseTime = requestEndTime - requestStartTime; // 计算响应时间

        // 记录响应时间到日志或者监控系统中
//        logger.info("Response time for /birth: {} ms,system and work:GetStudentByBirth,input:{},{},{},{},output:{}", responseTime,start,end,pageIndex,pageSize,studentsPage.toString());
//        System.out.println("Response time for /birth: " + responseTime + " ms");
        logger.info("Response received for /birth. LogID: {}", logID);
        logger.info("Response time: {} ms", responseTime);
        logger.info("Result: {}", studentsPage);
        return studentsPage;
    }


}
