package com.example.demo.controller;

import com.example.demo.entity.Students;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TestController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/test")
    public List<Students> list(){
        return studentRepository.selectList(null);
    }
}
