package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.AssetScrap;
import com.example.examsystembackend.service.AssetScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset/scrap")
@RequiredArgsConstructor
public class AssetScrapController {

    private final AssetScrapService assetScrapService;

    @GetMapping
    public List<AssetScrap> getAllScraps() {
        return assetScrapService.list();
    }

    @GetMapping("/{id}")
    public AssetScrap getScrapById(@PathVariable Long id) {
        return assetScrapService.getById(id);
    }

    @PostMapping
    public boolean createScrap(@RequestBody AssetScrap scrap) {
        return assetScrapService.save(scrap);
    }

    @PutMapping("/{id}")
    public boolean updateScrap(@PathVariable Long id, @RequestBody AssetScrap scrap) {
        scrap.setId(id);
        return assetScrapService.updateById(scrap);
    }

    @DeleteMapping("/{id}")
    public boolean deleteScrap(@PathVariable Long id) {
        return assetScrapService.removeById(id);
    }
}