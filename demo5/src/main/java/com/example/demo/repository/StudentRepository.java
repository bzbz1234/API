package com.example.demo.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Students;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentRepository extends BaseMapper<Students> {
}
