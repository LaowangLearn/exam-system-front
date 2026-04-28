package com.gov.affairs.entity.security;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_alarm")
public class SecurityAlarm implements Serializable {

    @TableId("alarm_id")
    private Long alarmId;

    private String alarmCode;

    private String alarmType;

    private String alarmLevel;

    private String alarmTitle;

    private String alarmContent;

    private Long deviceId;

    private String deviceCode;

    private String deviceName;

    private Long gridCellId;

    private String gridCellName;

    private String locationAddress;

    private java.math.BigDecimal locationLat;

    private java.math.BigDecimal locationLng;

    private LocalDateTime alarmTime;

    private String alarmSourceType;

    private String alarmSourceId;

    private String alarmPicUrls;

    private String alarmVideoUrl;

    private java.math.BigDecimal aiConfidence;

    private String aiAnalyzeResult;

    private String aiTargetInfo;

    private String targetTrackingJson;

    private Long handleDepartmentId;

    private String handleDepartmentName;

    private Long handleUserId;

    private String handleUserName;

    private LocalDateTime dispatchTime;

    private Long dispatchUserId;

    private String dispatchUserName;

    private LocalDateTime arriveTime;

    private LocalDateTime handleStartTime;

    private LocalDateTime handleEndTime;

    private String currentStatus;

    private String handleResult;

    private String handleDetail;

    private String handleMeaures;

    private String feedbackContent;

    private Integer evaluateScore;

    private String evaluateContent;

    private Integer responseTimeMinutes;

    private Integer resolveTimeMinutes;

    private Integer secretLevel;

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
