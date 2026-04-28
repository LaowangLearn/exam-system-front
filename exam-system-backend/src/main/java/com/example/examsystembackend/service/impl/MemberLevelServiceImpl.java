package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.MemberLevel;
import com.example.examsystembackend.mapper.MemberLevelMapper;
import com.example.examsystembackend.service.MemberLevelService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

    @Override
    public List<MemberLevel> listOrderBySort() {
        return baseMapper.selectOrderBySort();
    }

    @Override
    public MemberLevel getLevelByPoints(BigDecimal points) {
        List<MemberLevel> levels = listOrderBySort();
        for (int i = levels.size() - 1; i >= 0; i--) {
            MemberLevel level = levels.get(i);
            if (points.compareTo(level.getMinPoints()) >= 0) {
                return level;
            }
        }
        return levels.isEmpty() ? null : levels.get(0);
    }
}