package com.gov.affairs.service.document;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.document.OfficialDocument;
import com.gov.affairs.mapper.document.OfficialDocumentMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OfficialDocumentService extends ServiceImpl<OfficialDocumentMapper, OfficialDocument> {

    public PageResult<OfficialDocument> pageQuery(Integer pageNum, Integer pageSize, String docTitle, String docType,
                                                   String approvalStatus, String secretLevel, String startDate, String endDate) {
        Page<OfficialDocument> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<OfficialDocument> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(docTitle)) {
            wrapper.like(OfficialDocument::getDocTitle, docTitle);
        }
        if (StringUtils.hasText(docType)) {
            wrapper.eq(OfficialDocument::getDocType, docType);
        }
        if (StringUtils.hasText(approvalStatus)) {
            wrapper.eq(OfficialDocument::getApprovalStatus, approvalStatus);
        }
        if (StringUtils.hasText(secretLevel)) {
            wrapper.eq(OfficialDocument::getSecretLevel, secretLevel);
        }
        if (StringUtils.hasText(startDate)) {
            wrapper.ge(OfficialDocument::getIssueDate, startDate);
        }
        if (StringUtils.hasText(endDate)) {
            wrapper.le(OfficialDocument::getIssueDate, endDate);
        }
        wrapper.orderByDesc(OfficialDocument::getCreateTime);
        Page<OfficialDocument> result = this.page(page, wrapper);
        return PageResult.of(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }

    public boolean submitForApproval(Long docId, Long approvalFlowId) {
        OfficialDocument doc = this.getById(docId);
        if (doc == null) return false;
        doc.setApprovalFlowId(approvalFlowId);
        doc.setApprovalStatus("APPROVING");
        return this.updateById(doc);
    }

    public boolean approveDocument(Long docId, String approvalResult, String currentNode) {
        OfficialDocument doc = this.getById(docId);
        if (doc == null) return false;
        doc.setCurrentApprovalNode(currentNode);
        if ("APPROVED".equals(approvalResult)) {
            doc.setApprovalStatus("APPROVED");
        } else if ("REJECTED".equals(approvalResult)) {
            doc.setApprovalStatus("DRAFT");
        }
        return this.updateById(doc);
    }

    public boolean publishDocument(Long docId, Long signUserId, String signUserName) {
        OfficialDocument doc = this.getById(docId);
        if (doc == null) return false;
        doc.setApprovalStatus("PUBLISHED");
        doc.setSignUserId(signUserId);
        doc.setSignUserName(signUserName);
        doc.setSignTime(LocalDateTime.now());
        doc.setIssueDate(LocalDate.now());
        doc.setIssueTime(LocalDateTime.now());
        return this.updateById(doc);
    }

    public boolean archiveDocument(Long docId, String archiveInfo) {
        OfficialDocument doc = this.getById(docId);
        if (doc == null) return false;
        doc.setApprovalStatus("ARCHIVED");
        doc.setArchiveInfo(archiveInfo);
        return this.updateById(doc);
    }

    public boolean recallDocument(Long docId) {
        OfficialDocument doc = this.getById(docId);
        if (doc == null) return false;
        if (!"PUBLISHED".equals(doc.getApprovalStatus())) {
            return false;
        }
        doc.setApprovalStatus("RECALLED");
        return this.updateById(doc);
    }

    public Map<String, Object> getStatistics() {
        LambdaQueryWrapper<OfficialDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OfficialDocument::getStatus, 1);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", this.count(wrapper));

        LambdaQueryWrapper<OfficialDocument> draftWrapper = new LambdaQueryWrapper<>(wrapper);
        draftWrapper.eq(OfficialDocument::getApprovalStatus, "DRAFT");
        stats.put("draft", this.count(draftWrapper));

        LambdaQueryWrapper<OfficialDocument> approvingWrapper = new LambdaQueryWrapper<>(wrapper);
        approvingWrapper.eq(OfficialDocument::getApprovalStatus, "APPROVING");
        stats.put("approving", this.count(approvingWrapper));

        LambdaQueryWrapper<OfficialDocument> publishedWrapper = new LambdaQueryWrapper<>(wrapper);
        publishedWrapper.eq(OfficialDocument::getApprovalStatus, "PUBLISHED");
        stats.put("published", this.count(publishedWrapper));

        LambdaQueryWrapper<OfficialDocument> archivedWrapper = new LambdaQueryWrapper<>(wrapper);
        archivedWrapper.eq(OfficialDocument::getApprovalStatus, "ARCHIVED");
        stats.put("archived", this.count(archivedWrapper));

        return stats;
    }

    public List<OfficialDocument> getPendingApprovals(Long userId) {
        LambdaQueryWrapper<OfficialDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OfficialDocument::getApprovalStatus, "APPROVING");
        wrapper.orderByDesc(OfficialDocument::getCreateTime);
        return this.list(wrapper);
    }

    public List<OfficialDocument> getDeadlineWarnings() {
        return this.list(new LambdaQueryWrapper<OfficialDocument>()
            .eq(OfficialDocument::getApprovalStatus, "APPROVING")
            .apply("issue_date < {0}", LocalDate.now().plusDays(3)));
    }
}
