package com.example.examsystembackend.gov.controller.grid;

import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.gov.common.PageResult;
import com.example.examsystembackend.gov.entity.grid.GridCell;
import com.example.examsystembackend.gov.service.grid.GridCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grid/cell")
public class GridCellController {

    @Autowired
    private GridCellService gridCellService;

    @GetMapping("/page")
    public R<PageResult<GridCell>> pageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String cellName,
            @RequestParam(required = false) String cellType,
            @RequestParam(required = false) String levelCode) {
        return R.ok(gridCellService.pageQuery(pageNum, pageSize, cellName, cellType, levelCode));
    }

    @GetMapping("/list")
    public R<List<GridCell>> getTreeData() {
        return R.ok(gridCellService.getTreeData());
    }

    @GetMapping("/children/{parentId}")
    public R<List<GridCell>> getChildren(@PathVariable Long parentId) {
        return R.ok(gridCellService.getChildren(parentId));
    }

    @GetMapping("/{cellId}")
    public R<GridCell> getById(@PathVariable Long cellId) {
        return R.ok(gridCellService.getById(cellId));
    }

    @GetMapping("/code/{cellCode}")
    public R<GridCell> getByCellCode(@PathVariable String cellCode) {
        return R.ok(gridCellService.getByCellCode(cellCode));
    }

    @PostMapping
    public R<Boolean> create(@RequestBody GridCell gridCell) {
        return R.ok(gridCellService.save(gridCell));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody GridCell gridCell) {
        return R.ok(gridCellService.updateById(gridCell));
    }

    @DeleteMapping("/{cellId}")
    public R<Boolean> delete(@PathVariable Long cellId) {
        return R.ok(gridCellService.removeById(cellId));
    }

    @PutMapping("/status/{cellId}")
    public R<Boolean> updateStatus(@PathVariable Long cellId, @RequestParam Integer status) {
        GridCell cell = gridCellService.getById(cellId);
        if (cell == null) return R.fail("网格不存�?);
        cell.setStatus(status);
        return R.ok(gridCellService.updateById(cell));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(Map.of(
            "totalCells", gridCellService.count(),
            "activeCells", gridCellService.lambdaQuery().eq(GridCell::getStatus, 1).count()
        ));
    }
}
