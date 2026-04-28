package com.gov.affairs.controller.document;

import com.gov.affairs.common.R;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.document.OfficialDocument;
import com.gov.affairs.service.document.OfficialDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/document")
public class OfficialDocumentController {

    @Autowired
    private OfficialDocumentService officialDocumentService;

    @GetMapping("/page")
    public R<PageResult<OfficialDocument>> pageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String docTitle,
            @RequestParam(required = false) String docType,
            @RequestParam(required = false) String approvalStatus,
            @RequestParam(required = false) String secretLevel,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(officialDocumentService.pageQuery(pageNum, pageSize, docTitle, docType, approvalStatus, secretLevel, startDate, endDate));
    }

    @GetMapping("/{docId}")
    public R<OfficialDocument> getById(@PathVariable Long docId) {
        return R.ok(officialDocumentService.getById(docId));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(officialDocumentService.getStatistics());
    }

    @GetMapping("/pending/approvals")
    public R<List<OfficialDocument>> getPendingApprovals(@RequestParam(required = false) Long userId) {
        return R.ok(officialDocumentService.getPendingApprovals(userId));
    }

    @GetMapping("/deadline/warnings")
    public R<List<OfficialDocument>> getDeadlineWarnings() {
        return R.ok(officialDocumentService.getDeadlineWarnings());
    }

    @PostMapping
    public R<Boolean> create(@RequestBody OfficialDocument document) {
        document.setApprovalStatus("DRAFT");
        return R.ok(officialDocumentService.save(document));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody OfficialDocument document) {
        return R.ok(officialDocumentService.updateById(document));
    }

    @DeleteMapping("/{docId}")
    public R<Boolean> delete(@PathVariable Long docId) {
        return R.ok(officialDocumentService.removeById(docId));
    }

    @PutMapping("/submit/{docId}")
    public R<Boolean> submitForApproval(@PathVariable Long docId,
                                        @RequestParam(required = false) Long approvalFlowId) {
        return R.ok(officialDocumentService.submitForApproval(docId, approvalFlowId));
    }

    @PutMapping("/approve/{docId}")
    public R<Boolean> approveDocument(@PathVariable Long docId,
                                      @RequestParam String approvalResult,
                                      @RequestParam(required = false) String currentNode) {
        return R.ok(officialDocumentService.approveDocument(docId, approvalResult, currentNode));
    }

    @PutMapping("/publish/{docId}")
    public R<Boolean> publishDocument(@PathVariable Long docId,
                                      @RequestParam(required = false) Long signUserId,
                                      @RequestParam(required = false) String signUserName) {
        return R.ok(officialDocumentService.publishDocument(docId, signUserId, signUserName));
    }

    @PutMapping("/archive/{docId}")
    public R<Boolean> archiveDocument(@PathVariable Long docId,
                                      @RequestParam(required = false) String archiveInfo) {
        return R.ok(officialDocumentService.archiveDocument(docId, archiveInfo));
    }

    @PutMapping("/recall/{docId}")
    public R<Boolean> recallDocument(@PathVariable Long docId) {
        return R.ok(officialDocumentService.recallDocument(docId));
    }

    @GetMapping("/type/stats")
    public R<Map<String, Object>> getTypeStatistics() {
        return R.ok(Map.of(
            "notice", officialDocumentService.lambdaQuery().eq(OfficialDocument::getDocType, "NOTICE").count(),
            "command", officialDocumentService.lambdaQuery().eq(OfficialDocument::getDocType, "COMMAND").count(),
            "report", officialDocumentService.lambdaQuery().eq(OfficialDocument::getDocType, "REPORT").count(),
            "letter", officialDocumentService.lambdaQuery().eq(OfficialDocument::getDocType, "LETTER").count()
        ));
    }
}
