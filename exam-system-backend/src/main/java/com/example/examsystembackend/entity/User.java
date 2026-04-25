package com.example.examsystembackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Long departmentId;
    private String position;
    private String role;
    private String status;
    private String employeeId;
    private String gender;
    private LocalDate birthday;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String education;
    private String specialty;
    private String emergencyContact;
    private String emergencyPhone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
