package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    List<Customer> selectByCondition(
            @Param("name") String name,
            @Param("industry") String industry,
            @Param("level") String level,
            @Param("status") String status
    );
}