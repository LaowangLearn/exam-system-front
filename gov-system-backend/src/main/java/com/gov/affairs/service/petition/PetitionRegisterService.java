package com.gov.affairs.service.petition;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.petition.PetitionRegister;
import com.gov.affairs.mapper.petition.PetitionRegisterMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PetitionRegisterService extends ServiceImpl<PetitionRegisterMapper, PetitionRegister> {

    public PageResult<PetitionRegister> pageQuery(Integer pageNum, Integer pageSize, String title, String petitionType,
                                                   String currentStatus, String petitionerName, String startDate, String endDate) {
        Page<PetitionRegister> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PetitionRegister> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like(PetitionRegister::getTitle, title);
        }
        if (StringUtils.hasText(petitionType)) {
            wrapper.eq(PetitionRegister::getPetitionType, petitionType);
        }
        if (StringUtils.hasText(currentStatus)) {
            wrapper.eq(PetitionRegister::getCurrentStatus, currentStatus);
        }
        if (StringUtils.hasText(petitionerName)) {
            wrapper.like(PetitionRegister::getPetitionerName, petitionerName);
        }
        if (StringUtils.hasText(startDate)) {
            wrapper.ge(PetitionRegister::getCreateTime, startDate);
        }
        if (StringUtils.hasText(endDate)) {
            wrapper.le(PetitionRegister::getCreateTime, endDate);
        }
        wrapper.orderByDesc(PetitionRegister::getCreateTime);
        Page<PetitionRegister> result = this.page(page, wrapper);
        return PageResult.of(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }

    public boolean acceptPetition(Long petitionId, Long acceptDepartmentId, String acceptDepartmentName, String acceptWay) {
        PetitionRegister petition = this.getById(petitionId);
        if (petition == null) return false;
        petition.setAcceptDepartmentId(acceptDepartmentId);
        petition.setAcceptDepartmentName(acceptDepartmentName);
        petition.setAcceptWay(acceptWay);
        petition.setAcceptTime(LocalDateTime.now());
        petition.setAcceptResult("ACCEPT");
        petition.setCurrentStatus("ACCEPTED");
        return this.updateById(petition);
    }

    public boolean guidePetition(Long petitionId, Long guideDepartmentId) {
        PetitionRegister petition = this.getById(petitionId);
        if (petition == null) return false;
        petition.setGuideDepartmentId(guideDepartmentId);
        petition.setGuideTime(LocalDateTime.now());
        petition.setAcceptResult("TRANSFER");
        petition.setCurrentStatus("PENDING");
        return this.updateById(petition);
    }

    public boolean assignHandler(Long petitionId, Long handleDepartmentId, String handleDepartmentName,
                                Long handleUserId, String handleUserName, LocalDateTime deadline) {
        PetitionRegister petition = this.getById(petitionId);
        if (petition == null) return false;
        petition.setHandleDepartmentId(handleDepartmentId);
        petition.setHandleDepartmentName(handleDepartmentName);
        petition.setHandleUserId(handleUserId);
        petition.setHandleUserName(handleUserName);
        petition.setDeadline(deadline);
        petition.setCurrentStatus("HANDLING");
        return this.updateById(petition);
    }

    public boolean completePetition(Long petitionId, String handleResult, String handleMeaures) {
        PetitionRegister petition = this.getById(petitionId);
        if (petition == null) return false;
        petition.setHandleResult(handleResult);
        petition.setHandleMeaures(handleMeaures);
        petition.setCurrentStatus("COMPLETED");
        petition.setCloseTime(LocalDateTime.now());
        return this.updateById(petition);
    }

    public boolean evaluatePetition(Long petitionId, Integer satisfactionScore, String petitionFeedback) {
        PetitionRegister petition = this.getById(petitionId);
        if (petition == null) return false;
        petition.setSatisfactionScore(satisfactionScore);
        petition.setPetitionFeedback(petitionFeedback);
        petition.setCurrentStatus("CLOSED");
        return this.updateById(petition);
    }

    public Map<String, Object> getStatistics() {
        LambdaQueryWrapper<PetitionRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PetitionRegister::getStatus, 1);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", this.count(wrapper));

        LambdaQueryWrapper<PetitionRegister> pendingWrapper = new LambdaQueryWrapper<>(wrapper);
        pendingWrapper.eq(PetitionRegister::getCurrentStatus, "PENDING");
        stats.put("pending", this.count(pendingWrapper));

        LambdaQueryWrapper<PetitionRegister> handlingWrapper = new LambdaQueryWrapper<>(wrapper);
        handlingWrapper.eq(PetitionRegister::getCurrentStatus, "HANDLING");
        stats.put("handling", this.count(handlingWrapper));

        LambdaQueryWrapper<PetitionRegister> completedWrapper = new LambdaQueryWrapper<>(wrapper);
        completedWrapper.eq(PetitionRegister::getCurrentStatus, "CLOSED");
        stats.put("completed", this.count(completedWrapper));

        LambdaQueryWrapper<PetitionRegister> keyCaseWrapper = new LambdaQueryWrapper<>(wrapper);
        keyCaseWrapper.eq(PetitionRegister::getIsKeyCase, 1);
        stats.put("keyCases", this.count(keyCaseWrapper));

        return stats;
    }

    public List<PetitionRegister> getDeadlineWarnings() {
        LambdaQueryWrapper<PetitionRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNotNull(PetitionRegister::getDeadline);
        wrapper.eq(PetitionRegister::getCurrentStatus, "HANDLING");
        wrapper.apply("deadline < {0}", LocalDateTime.now().plusHours(24));
        return this.list(wrapper);
    }

    public List<PetitionRegister> getSimilarCases(Long petitionId) {
        PetitionRegister current = this.getById(petitionId);
        if (current == null) return null;

        LambdaQueryWrapper<PetitionRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(PetitionRegister::getPetitionType, current.getPetitionType());
        wrapper.like(PetitionRegister::getPetitionCategory, current.getPetitionCategory());
        wrapper.ne(PetitionRegister::getPetitionId, petitionId);
        wrapper.orderByDesc(PetitionRegister::getCreateTime);
        wrapper.last("LIMIT 5");
        return this.list(wrapper);
    }
}
