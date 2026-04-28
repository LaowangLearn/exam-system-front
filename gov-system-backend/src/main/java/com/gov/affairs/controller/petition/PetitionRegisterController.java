package com.gov.affairs.controller.petition;

import com.gov.affairs.common.R;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.petition.PetitionRegister;
import com.gov.affairs.service.petition.PetitionRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/petition/register")
public class PetitionRegisterController {

    @Autowired
    private PetitionRegisterService petitionRegisterService;

    @GetMapping("/page")
    public R<PageResult<PetitionRegister>> pageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String petitionType,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String petitionerName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(petitionRegisterService.pageQuery(pageNum, pageSize, title, petitionType, currentStatus, petitionerName, startDate, endDate));
    }

    @GetMapping("/{petitionId}")
    public R<PetitionRegister> getById(@PathVariable Long petitionId) {
        return R.ok(petitionRegisterService.getById(petitionId));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(petitionRegisterService.getStatistics());
    }

    @GetMapping("/deadline/warnings")
    public R<List<PetitionRegister>> getDeadlineWarnings() {
        return R.ok(petitionRegisterService.getDeadlineWarnings());
    }

    @GetMapping("/similar/{petitionId}")
    public R<List<PetitionRegister>> getSimilarCases(@PathVariable Long petitionId) {
        return R.ok(petitionRegisterService.getSimilarCases(petitionId));
    }

    @PostMapping
    public R<Boolean> create(@RequestBody PetitionRegister petition) {
        petition.setCurrentStatus("PENDING");
        petition.setPetitionType(petition.getPetitionType() != null ? petition.getPetitionType() : "LETTER");
        petition.setPetitionCategory(petition.getPetitionCategory() != null ? petition.getPetitionCategory() : "NORMAL");
        return R.ok(petitionRegisterService.save(petition));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody PetitionRegister petition) {
        return R.ok(petitionRegisterService.updateById(petition));
    }

    @DeleteMapping("/{petitionId}")
    public R<Boolean> delete(@PathVariable Long petitionId) {
        return R.ok(petitionRegisterService.removeById(petitionId));
    }

    @PutMapping("/accept/{petitionId}")
    public R<Boolean> acceptPetition(@PathVariable Long petitionId,
                                      @RequestParam Long acceptDepartmentId,
                                      @RequestParam String acceptDepartmentName,
                                      @RequestParam(required = false) String acceptWay) {
        return R.ok(petitionRegisterService.acceptPetition(petitionId, acceptDepartmentId, acceptDepartmentName, acceptWay));
    }

    @PutMapping("/guide/{petitionId}")
    public R<Boolean> guidePetition(@PathVariable Long petitionId,
                                    @RequestParam Long guideDepartmentId) {
        return R.ok(petitionRegisterService.guidePetition(petitionId, guideDepartmentId));
    }

    @PutMapping("/assign/{petitionId}")
    public R<Boolean> assignHandler(@PathVariable Long petitionId,
                                    @RequestParam Long handleDepartmentId,
                                    @RequestParam String handleDepartmentName,
                                    @RequestParam(required = false) Long handleUserId,
                                    @RequestParam(required = false) String handleUserName,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime deadline) {
        return R.ok(petitionRegisterService.assignHandler(petitionId, handleDepartmentId, handleDepartmentName, handleUserId, handleUserName, deadline));
    }

    @PutMapping("/complete/{petitionId}")
    public R<Boolean> completePetition(@PathVariable Long petitionId,
                                       @RequestParam String handleResult,
                                       @RequestParam(required = false) String handleMeaures) {
        return R.ok(petitionRegisterService.completePetition(petitionId, handleResult, handleMeaures));
    }

    @PutMapping("/evaluate/{petitionId}")
    public R<Boolean> evaluatePetition(@PathVariable Long petitionId,
                                       @RequestParam Integer satisfactionScore,
                                       @RequestParam(required = false) String petitionFeedback) {
        return R.ok(petitionRegisterService.evaluatePetition(petitionId, satisfactionScore, petitionFeedback));
    }
}
