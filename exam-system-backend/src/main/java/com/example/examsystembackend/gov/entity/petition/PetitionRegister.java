package com.example.examsystembackend.gov.entity.petition;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("petition_register")
public class PetitionRegister implements Serializable {

    @TableId("petition_id")
    private Long petitionId;

    private String petitionCode;

    private String petitionType;

    private String petitionCategory;

    private String title;

    private String content;

    private String appealItems;

    private String evidenceMaterials;

    private String petitionerName;

    private String petitionerPhone;

    private String petitionerIdCard;

    private String petitionerAddress;

    private String petitionerType;

    private Integer isAnonymous;

    private Integer isKeyCase;

    private Integer isRepetitive;

    private String relatedPetitionIds;

    private LocalDate happenDate;

    private String happenPlace;

    private String happenRegionCode;

    private String involvedOrganizationIds;

    private String involvedOrganizationNames;

    private java.math.BigDecimal involvedAmount;

    private Integer secretLevel;

    private Long acceptDepartmentId;

    private String acceptDepartmentName;

    private LocalDateTime acceptTime;

    private String acceptWay;

    private String acceptResult;

    private Long guideDepartmentId;

    private LocalDateTime guideTime;

    private LocalDateTime deadline;

    private Long handleDepartmentId;

    private String handleDepartmentName;

    private Long handleUserId;

    private String handleUserName;

    private String currentStatus;

    private String processNodesJson;

    private String handleResult;

    private String handleMeaures;

    private Integer satisfactionScore;

    private String petitionFeedback;

    private LocalDateTime closeTime;

    private String aiAnalyzeResult;

    private String aiSimilarCases;

    private String aiRiskLevel;

    private String aiSuggestion;

    private String gmEncryptedFields;

    private String sourceChannel;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
