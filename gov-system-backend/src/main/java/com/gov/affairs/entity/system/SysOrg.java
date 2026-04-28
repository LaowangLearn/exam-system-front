package com.gov.affairs.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_org")
public class SysOrg implements Serializable {

    @TableId("org_id")
    private Long orgId;

    private String orgCode;

    private String orgName;

    private String orgType;

    private Long parentId;

    private String levelCode;

    private String unifiedSocialCreditCode;

    private String legalPerson;

    private String contactPhone;

    private String address;

    private java.math.BigDecimal latitude;

    private java.math.BigDecimal longitude;

    private String gmEncryptedFields;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
