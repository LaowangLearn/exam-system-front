package com.example.examsystembackend.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ExamStatus {
    DRAFT(0, "草稿"),
    COMMITTED(1, "已提交");

    @EnumValue
    private final Integer code;
    private final String desc;

    ExamStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}