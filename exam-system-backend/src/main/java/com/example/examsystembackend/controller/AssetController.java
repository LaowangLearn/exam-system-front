package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Asset;
import com.example.examsystembackend.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.list();
    }

    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Long id) {
        return assetService.getById(id);
    }

    @PostMapping
    public boolean createAsset(@RequestBody Asset asset) {
        return assetService.save(asset);
    }

    @PutMapping("/{id}")
    public boolean updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        asset.setId(id);
        return assetService.updateById(asset);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAsset(@PathVariable Long id) {
        return assetService.removeById(id);
    }
}