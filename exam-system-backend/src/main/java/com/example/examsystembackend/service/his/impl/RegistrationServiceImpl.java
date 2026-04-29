package com.example.examsystembackend.service.his.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.his.Registration;
import com.example.examsystembackend.mapper.his.RegistrationMapper;
import com.example.examsystembackend.service.his.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Override
    public Page<Registration> queryPage(Long pageNum, Long pageSize, String patientName, String departmentId, String visitStatus) {
        Page<Registration> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Registration> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(patientName)) {
            wrapper.like(Registration::getPatientName, patientName);
        }
        if (StringUtils.hasText(departmentId)) {
            wrapper.eq(Registration::getDepartmentId, departmentId);
        }
        if (StringUtils.hasText(visitStatus)) {
            wrapper.eq(Registration::getVisitStatus, visitStatus);
        }
        wrapper.orderByDesc(Registration::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public Registration createRegistration(Registration registration) {
        registration.setVisitStatus("PENDING");
        registration.setPaymentStatus("UNPAID");
        save(registration);
        return registration;
    }

    @Override
    public void cancelRegistration(String registrationId) {
        Registration registration = getById(registrationId);
        if (registration != null) {
            registration.setVisitStatus("CANCELLED");
            updateById(registration);
        }
    }
}
