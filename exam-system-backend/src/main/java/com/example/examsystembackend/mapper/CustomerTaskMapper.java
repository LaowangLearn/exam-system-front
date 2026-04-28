package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.CustomerTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerTaskMapper extends BaseMapper<CustomerTask> {
    List<CustomerTask> selectByCustomerId(@Param("customerId") Long customerId);
    List<CustomerTask> selectByAssigneeId(@Param("assigneeId") Long assigneeId);
}