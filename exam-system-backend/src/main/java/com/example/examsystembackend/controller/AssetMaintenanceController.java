package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.AssetMaintenance;
import com.example.examsystembackend.service.AssetMaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset/maintenance")
@RequiredArgsConstructor
public class AssetMaintenanceController {

    private final AssetMaintenanceService assetMaintenanceService;

    @GetMapping
    public List<AssetMaintenance> getAllMaintenances() {
        return assetMaintenanceService.list();
    }

    @GetMapping("/{id}")
    public AssetMaintenance getMaintenanceById(@PathVariable Long id) {
        return assetMaintenanceService.getById(id);
    }

    @PostMapping
    public boolean createMaintenance(@RequestBody AssetMaintenance maintenance) {
        return assetMaintenanceService.save(maintenance);
    }

    @PutMapping("/{id}")
    public boolean updateMaintenance(@PathVariable Long id, @RequestBody AssetMaintenance maintenance) {
        maintenance.setId(id);
        return assetMaintenanceService.updateById(maintenance);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMaintenance(@PathVariable Long id) {
        return assetMaintenanceService.removeById(id);
    }
}