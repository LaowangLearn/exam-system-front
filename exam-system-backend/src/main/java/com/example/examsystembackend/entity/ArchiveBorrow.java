package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("archive_borrow")
public class ArchiveBorrow {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long archiveId;
    private Long userId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String purpose;
    private String status;
    private Long approverId;
    private String approvalComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}