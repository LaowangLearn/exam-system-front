package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.AssetRequisition;
import com.example.examsystembackend.service.AssetRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset/requisition")
@RequiredArgsConstructor
public class AssetRequisitionController {

    private final AssetRequisitionService assetRequisitionService;

    @GetMapping
    public List<AssetRequisition> getAllRequisitions() {
        return assetRequisitionService.list();
    }

    @GetMapping("/{id}")
    public AssetRequisition getRequisitionById(@PathVariable Long id) {
        return assetRequisitionService.getById(id);
    }

    @PostMapping
    public boolean createRequisition(@RequestBody AssetRequisition requisition) {
        return assetRequisitionService.save(requisition);
    }

    @PutMapping("/{id}")
    public boolean updateRequisition(@PathVariable Long id, @RequestBody AssetRequisition requisition) {
        requisition.setId(id);
        return assetRequisitionService.updateById(requisition);
    }

    @DeleteMapping("/{id}")
    public boolean deleteRequisition(@PathVariable Long id) {
        return assetRequisitionService.removeById(id);
    }
}