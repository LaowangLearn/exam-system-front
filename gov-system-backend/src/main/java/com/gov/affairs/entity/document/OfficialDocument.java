package com.gov.affairs.entity.document;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("official_document")
public class OfficialDocument implements Serializable {

    @TableId("doc_id")
    private Long docId;

    private String docCode;

    private String docType;

    private String docTitle;

    private String docSubject;

    private Integer secretLevel;

    private String urgencyLevel;

    private String main送UnitIds;

    private String main送UnitNames;

    private String ccUnitIds;

    private String ccUnitNames;

    private Long issueDepartmentId;

    private String issueDepartmentName;

    private Long issueUserId;

    private String issueUserName;

    private String draftContent;

    private String attachmentsJson;

    private Integer pageCount;

    private Integer editionCount;

    private Integer wordCount;

    private String referenceDocCodes;

    private String relatedDocIds;

    private Long approvalFlowId;

    private String currentApprovalNode;

    private String approvalStatus;

    private LocalDate issueDate;

    private LocalDateTime issueTime;

    private Long signUserId;

    private String signUserName;

    private LocalDateTime signTime;

    private Long printUserId;

    private String printUserName;

    private LocalDateTime printTime;

    private Integer printCount;

    private String receiveConfirmJson;

    private String readConfirmJson;

    private String archiveInfo;

    private Long archiveId;

    private String gmEncryptedContent;

    private String gmSignature;

    private String gmHash;

    private String aiSummary;

    private String aiKeyPoints;

    private String aiExtractTasks;

    private String aiDeadlineWarning;

    private String digitalSignature;

    private String blockchainHash;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
