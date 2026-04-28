package com.example.examsystembackend.gov.service.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.gov.common.PageResult;
import com.example.examsystembackend.gov.entity.security.SecurityDevice;
import com.example.examsystembackend.gov.mapper.security.SecurityDeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SecurityDeviceService extends ServiceImpl<SecurityDeviceMapper, SecurityDevice> {

    public PageResult<SecurityDevice> pageQuery(Integer pageNum, Integer pageSize, String deviceName, String deviceType,
                                                  String deviceStatus, String gridCellId) {
        Page<SecurityDevice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SecurityDevice> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(deviceName)) {
            wrapper.like(SecurityDevice::getDeviceName, deviceName);
        }
        if (StringUtils.hasText(deviceType)) {
            wrapper.eq(SecurityDevice::getDeviceType, deviceType);
        }
        if (StringUtils.hasText(deviceStatus)) {
            wrapper.eq(SecurityDevice::getDeviceStatus, deviceStatus);
        }
        if (StringUtils.hasText(gridCellId)) {
            wrapper.eq(SecurityDevice::getInstallGridCellId, gridCellId);
        }
        wrapper.orderByDesc(SecurityDevice::getCreateTime);
        Page<SecurityDevice> result = this.page(page, wrapper);
        return PageResult.of(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }

    public boolean updateDeviceStatus(Long deviceId, String status) {
        SecurityDevice device = this.getById(deviceId);
        if (device == null) return false;
        device.setDeviceStatus(status);
        device.setLastHeartbeatTime(LocalDateTime.now());
        if ("ONLINE".equals(status)) {
            device.setLastOnlineTime(LocalDateTime.now());
        }
        return this.updateById(device);
    }

    public boolean heartbeat(Long deviceId) {
        SecurityDevice device = this.getById(deviceId);
        if (device == null) return false;
        device.setLastHeartbeatTime(LocalDateTime.now());
        if (!"ONLINE".equals(device.getDeviceStatus())) {
            device.setDeviceStatus("ONLINE");
        }
        return this.updateById(device);
    }

    public Map<String, Object> getStatistics() {
        LambdaQueryWrapper<SecurityDevice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityDevice::getStatus, 1);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", this.count(wrapper));

        LambdaQueryWrapper<SecurityDevice> onlineWrapper = new LambdaQueryWrapper<>(wrapper);
        onlineWrapper.eq(SecurityDevice::getDeviceStatus, "ONLINE");
        stats.put("online", this.count(onlineWrapper));

        LambdaQueryWrapper<SecurityDevice> offlineWrapper = new LambdaQueryWrapper<>(wrapper);
        offlineWrapper.eq(SecurityDevice::getDeviceStatus, "OFFLINE");
        stats.put("offline", this.count(offlineWrapper));

        LambdaQueryWrapper<SecurityDevice> faultWrapper = new LambdaQueryWrapper<>(wrapper);
        faultWrapper.eq(SecurityDevice::getDeviceStatus, "FAULT");
        stats.put("fault", this.count(faultWrapper));

        LambdaQueryWrapper<SecurityDevice> cameraWrapper = new LambdaQueryWrapper<>(wrapper);
        cameraWrapper.eq(SecurityDevice::getDeviceType, "CAMERA");
        stats.put("cameraCount", this.count(cameraWrapper));

        return stats;
    }

    public List<SecurityDevice> getByGridCellId(Long gridCellId) {
        LambdaQueryWrapper<SecurityDevice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityDevice::getInstallGridCellId, gridCellId);
        wrapper.eq(SecurityDevice::getStatus, 1);
        return this.list(wrapper);
    }

    public List<SecurityDevice> getByDeviceType(String deviceType) {
        LambdaQueryWrapper<SecurityDevice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityDevice::getDeviceType, deviceType);
        wrapper.eq(SecurityDevice::getStatus, 1);
        return this.list(wrapper);
    }

    public List<SecurityDevice> getOfflineDevices() {
        LambdaQueryWrapper<SecurityDevice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityDevice::getDeviceStatus, "OFFLINE");
        wrapper.eq(SecurityDevice::getStatus, 1);
        wrapper.apply("last_heartbeat_time < {0}", LocalDateTime.now().minusMinutes(30));
        return this.list(wrapper);
    }

    public List<SecurityDevice> getMaintenanceDue() {
        LambdaQueryWrapper<SecurityDevice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityDevice::getStatus, 1);
        wrapper.apply("warranty_end_date < {0}", LocalDateTime.now().plusMonths(1));
        return this.list(wrapper);
    }
}
