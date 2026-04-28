package com.gov.affairs.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId("user_id")
    private Long userId;

    private String username;

    private String password;

    private String realName;

    private String idCard;

    private String phone;

    private String email;

    private Long orgId;

    private String position;

    private String rankLevel;

    private String userType;

    private Integer secretLevel;

    private String aiModelPermission;

    private String lastLoginIp;

    private LocalDateTime lastLoginTime;

    private LocalDateTime passwordExpireTime;

    private Integer passwordChangeRequired;

    private Integer riskLevel;

    private String gmPublicKey;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
