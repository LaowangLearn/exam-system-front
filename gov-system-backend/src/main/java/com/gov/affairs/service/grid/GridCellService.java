package com.gov.affairs.service.grid;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.grid.GridCell;
import com.gov.affairs.mapper.grid.GridCellMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GridCellService extends ServiceImpl<GridCellMapper, GridCell> {

    public PageResult<GridCell> pageQuery(Integer pageNum, Integer pageSize, String cellName, String cellType, String levelCode) {
        Page<GridCell> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<GridCell> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(cellName)) {
            wrapper.like(GridCell::getCellName, cellName);
        }
        if (StringUtils.hasText(cellType)) {
            wrapper.eq(GridCell::getCellType, cellType);
        }
        if (StringUtils.hasText(levelCode)) {
            wrapper.likeRight(GridCell::getLevelCode, levelCode);
        }
        wrapper.orderByAsc(GridCell::getLevelCode);
        Page<GridCell> result = this.page(page, wrapper);
        return PageResult.of(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }

    public List<GridCell> getTreeData() {
        LambdaQueryWrapper<GridCell> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GridCell::getStatus, 1);
        wrapper.orderByAsc(GridCell::getLevelCode);
        return this.list(wrapper);
    }

    public List<GridCell> getChildren(Long parentId) {
        LambdaQueryWrapper<GridCell> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GridCell::getParentId, parentId);
        wrapper.eq(GridCell::getStatus, 1);
        wrapper.orderByAsc(GridCell::getCellCode);
        return this.list(wrapper);
    }

    public GridCell getByCellCode(String cellCode) {
        LambdaQueryWrapper<GridCell> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GridCell::getCellCode, cellCode);
        return this.getOne(wrapper);
    }
}
