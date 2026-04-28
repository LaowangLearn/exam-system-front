package com.example.examsystembackend.gov.entity.security;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_device")
public class SecurityDevice implements Serializable {

    @TableId("device_id")
    private Long deviceId;

    private String deviceCode;

    private String deviceName;

    private String deviceType;

    private String deviceSubType;

    private String manufacturer;

    private String model;

    private String serialNumber;

    private String macAddress;

    private String ipAddress;

    private Integer portNumber;

    private String installLocation;

    private Long installGridCellId;

    private java.math.BigDecimal installLat;

    private java.math.BigDecimal installLng;

    private java.math.BigDecimal directionAngle;

    private java.math.BigDecimal coverageRadius;

    private LocalDate installDate;

    private LocalDate warrantyEndDate;

    private String associatedDeviceIds;

    private String linkedChannelIds;

    private String videoStreamUrl;

    private Integer snapshotInterval;

    private Integer recordStorageDays;

    private String alarmThresholdJson;

    private Integer aiAnalysisEnabled;

    private String aiAnalysisTypes;

    private String deviceStatus;

    private LocalDateTime lastHeartbeatTime;

    private LocalDateTime lastOnlineTime;

    private Integer healthScore;

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
