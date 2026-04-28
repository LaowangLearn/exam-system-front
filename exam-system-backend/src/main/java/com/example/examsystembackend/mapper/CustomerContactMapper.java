package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.CustomerContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerContactMapper extends BaseMapper<CustomerContact> {
    List<CustomerContact> selectByCustomerId(@Param("customerId") Long customerId);
}