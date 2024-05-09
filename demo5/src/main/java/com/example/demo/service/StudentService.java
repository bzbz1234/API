package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Students;

import java.time.LocalDate;

public interface StudentService extends IService<Students> {
    Page<Students> findStudentByBirthBetween(LocalDate startBirth, LocalDate ednBirth, Integer pageIndex, Integer pageSize);
}
