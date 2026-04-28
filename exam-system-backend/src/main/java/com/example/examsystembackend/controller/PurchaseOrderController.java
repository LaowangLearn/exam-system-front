package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.PurchaseOrder;
import com.example.examsystembackend.entity.PurchaseOrderItem;
import com.example.examsystembackend.service.PurchaseOrderItemService;
import com.example.examsystembackend.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scm/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseOrderItemService purchaseOrderItemService;

    @GetMapping
    public IPage<PurchaseOrder> list(@RequestParam(defaultValue = "1") int page, 
                                     @RequestParam(defaultValue = "10") int size) {
        return purchaseOrderService.page(new Page<>(page, size));
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("order", purchaseOrderService.getById(id));
        result.put("items", purchaseOrderItemService.getByOrderId(id));
        return result;
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrder> getBySupplierId(@PathVariable Long supplierId) {
        return purchaseOrderService.getBySupplierId(supplierId);
    }

    @PostMapping
    public PurchaseOrder create(@RequestBody Map<String, Object> request) {
        PurchaseOrder order = new PurchaseOrder();
        order.setOrderCode(purchaseOrderService.generateOrderCode());
        order.setSupplierId(Long.valueOf(request.get("supplierId").toString()));
        order.setSupplierName(request.get("supplierName").toString());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setCreateBy(Long.valueOf(request.get("createBy").toString()));
        order.setCreateTime(LocalDateTime.now());
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        BigDecimal totalAmount = BigDecimal.ZERO;
        int itemCount = 0;
        
        purchaseOrderService.save(order);
        
        for (Map<String, Object> item : items) {
            PurchaseOrderItem orderItem = new PurchaseOrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductCode(item.get("productCode").toString());
            orderItem.setProductName(item.get("productName").toString());
            orderItem.setSpec(item.get("spec").toString());
            orderItem.setUnit(item.get("unit").toString());
            orderItem.setQuantity(Integer.valueOf(item.get("quantity").toString()));
            orderItem.setUnitPrice(new BigDecimal(item.get("unitPrice").toString()));
            BigDecimal amount = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            orderItem.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            itemCount++;
            purchaseOrderItemService.save(orderItem);
        }
        
        order.setTotalAmount(totalAmount);
        order.setItemCount(itemCount);
        purchaseOrderService.updateById(order);
        
        return order;
    }

    @PutMapping("/{id}")
    public PurchaseOrder update(@PathVariable Long id, @RequestBody PurchaseOrder order) {
        order.setId(id);
        order.setUpdateTime(LocalDateTime.now());
        purchaseOrderService.updateById(order);
        return order;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        purchaseOrderItemService.lambdaQuery().eq(PurchaseOrderItem::getOrderId, id).list().forEach(item -> {
            purchaseOrderItemService.removeById(item.getId());
        });
        return purchaseOrderService.removeById(id);
    }
}