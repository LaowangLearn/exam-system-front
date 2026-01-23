package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.Module;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模块数据访问层
 */
@Mapper
public interface ModuleMapper extends BaseMapper<Module> {
}