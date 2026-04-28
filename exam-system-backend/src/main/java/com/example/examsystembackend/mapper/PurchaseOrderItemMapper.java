package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseOrderItemMapper extends BaseMapper<PurchaseOrderItem> {
    List<PurchaseOrderItem> selectByOrderId(@Param("orderId") Long orderId);
}