package com.example.examsystembackend.gov.entity.grid;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grid_cell")
public class GridCell implements Serializable {

    @TableId("cell_id")
    private Long cellId;

    private String cellCode;

    private String cellName;

    private String cellType;

    private Long parentId;

    private String levelCode;

    private String boundaryGeojson;

    private String centerPoint;

    private java.math.BigDecimal areaSqm;

    private Integer populationCount;

    private Integer householdCount;

    private Long responsibleUserId;

    private String responsiblePhone;

    private Integer secretLevel;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
