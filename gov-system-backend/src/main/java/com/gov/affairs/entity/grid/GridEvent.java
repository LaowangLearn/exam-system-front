package com.gov.affairs.entity.grid;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grid_event")
public class GridEvent implements Serializable {

    @TableId("event_id")
    private Long eventId;

    private String eventCode;

    private String eventTitle;

    private String eventType;

    private String eventTypeDetail;

    private String urgentLevel;

    private Long gridCellId;

    private String gridCellCode;

    private String gridCellName;

    private String address;

    private java.math.BigDecimal locationLat;

    private java.math.BigDecimal locationLng;

    private String description;

    private String reporterName;

    private String reporterPhone;

    private String reporterType;

    private String photosJson;

    private String audioUrl;

    private String videoUrl;

    private Long handleDepartmentId;

    private String handleDepartmentName;

    private String cooperateDepartmentIds;

    private LocalDateTime deadline;

    private LocalDateTime reportTime;

    private LocalDateTime assignTime;

    private LocalDateTime acceptTime;

    private LocalDateTime startHandleTime;

    private LocalDateTime completeTime;

    private LocalDateTime closeTime;

    private String currentStatus;

    private String handleResult;

    private String handleDetail;

    private Integer satisfactionScore;

    private String citizenFeedback;

    private LocalDateTime evaluationTime;

    private Integer secretLevel;

    private String gmEncryptedFields;

    private String sourceSystem;

    private String sourceEventId;

    private String aiSuggestion;

    private String aiRiskLevel;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
