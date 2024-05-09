package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Students;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentRepository, Students> implements StudentService {

    @Override
    public Page<Students> findStudentByBirthBetween(LocalDate startDate, LocalDate endDate, Integer pageIndex, Integer pageSize) {
        LambdaQueryWrapper<Students> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Students::getBirth,startDate,endDate);
        Page<Students>page=page(new Page<>(
                pageIndex,
                pageSize
        ),wrapper);
        return page;
    }
}
