package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Order;
import com.example.examsystembackend.entity.OrderItem;
import com.example.examsystembackend.entity.Product;
import com.example.examsystembackend.service.OrderItemService;
import com.example.examsystembackend.service.OrderService;
import com.example.examsystembackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    @GetMapping
    public IPage<Order> list(@RequestParam(defaultValue = "1") int page, 
                             @RequestParam(defaultValue = "10") int size) {
        return orderService.page(new Page<>(page, size));
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("order", orderService.getById(id));
        result.put("items", orderItemService.getByOrderId(id));
        return result;
    }

    @GetMapping("/user/{userId}")
    public List<Order> getByUserId(@PathVariable Long userId) {
        return orderService.getByUserId(userId);
    }

    @PostMapping
    public Order create(@RequestBody Map<String, Object> request) {
        Order order = new Order();
        order.setOrderNo(orderService.generateOrderNo());
        order.setUserId(Long.valueOf(request.get("userId").toString()));
        order.setUserName(request.get("userName").toString());
        order.setPhone(request.get("phone").toString());
        order.setAddress(request.get("address").toString());
        order.setPayMethod(request.get("payMethod").toString());
        order.setPayStatus("UNPAID");
        order.setStatus("PENDING");
        order.setOrderTime(LocalDateTime.now());
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        orderService.save(order);
        
        for (Map<String, Object> item : items) {
            Long productId = Long.valueOf(item.get("productId").toString());
            int quantity = Integer.valueOf(item.get("quantity").toString());
            
            Product product = productService.getById(productId);
            if (product != null && product.getStock() >= quantity) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getId());
                orderItem.setProductId(productId);
                orderItem.setProductCode(product.getProductCode());
                orderItem.setProductName(product.getName());
                orderItem.setSpec(product.getSpec());
                orderItem.setQuantity(quantity);
                orderItem.setUnitPrice(product.getPrice());
                BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                orderItem.setAmount(amount);
                totalAmount = totalAmount.add(amount);
                orderItemService.save(orderItem);
                
                product.setStock(product.getStock() - quantity);
                product.setSales(product.getSales() + quantity);
                productService.updateById(product);
            }
        }
        
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setPayAmount(totalAmount);
        orderService.updateById(order);
        
        return order;
    }

    @PutMapping("/{id}/pay")
    public Order pay(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null && "UNPAID".equals(order.getPayStatus())) {
            order.setPayStatus("PAID");
            order.setPayTime(LocalDateTime.now());
            order.setStatus("PAID");
            orderService.updateById(order);
        }
        return order;
    }

    @PutMapping("/{id}/ship")
    public Order ship(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null && "PAID".equals(order.getPayStatus())) {
            order.setStatus("SHIPPED");
            order.setShipTime(LocalDateTime.now());
            orderService.updateById(order);
        }
        return order;
    }

    @PutMapping("/{id}/complete")
    public Order complete(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null && "SHIPPED".equals(order.getStatus())) {
            order.setStatus("COMPLETED");
            order.setCompleteTime(LocalDateTime.now());
            orderService.updateById(order);
        }
        return order;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        orderItemService.lambdaQuery().eq(OrderItem::getOrderId, id).list().forEach(item -> {
            orderItemService.removeById(item.getId());
        });
        return orderService.removeById(id);
    }
}