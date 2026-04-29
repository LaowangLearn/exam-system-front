package com.example.examsystembackend.service.his;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.his.Registration;

public interface RegistrationService extends IService<Registration> {
    Page<Registration> queryPage(Long pageNum, Long pageSize, String patientName, String departmentId, String visitStatus);
    Registration createRegistration(Registration registration);
    void cancelRegistration(String registrationId);
}
