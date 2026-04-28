package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.MemberLevel;

import java.math.BigDecimal;
import java.util.List;

public interface MemberLevelService extends IService<MemberLevel> {
    List<MemberLevel> listOrderBySort();
    MemberLevel getLevelByPoints(BigDecimal points);
}