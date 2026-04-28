package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Product;
import com.example.examsystembackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public IPage<Product> list(@RequestParam(defaultValue = "1") int page, 
                               @RequestParam(defaultValue = "10") int size) {
        return productService.page(new Page<>(page, size));
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        return productService.getByCategory(category);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String name) {
        return productService.search(name);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        product.setProductCode(productService.generateProductCode());
        product.setCreateTime(LocalDateTime.now());
        productService.save(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        product.setUpdateTime(LocalDateTime.now());
        productService.updateById(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return productService.removeById(id);
    }

    @GetMapping("/statistics")
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", productService.count());
        result.put("active", productService.lambdaQuery().eq(Product::getStatus, "ACTIVE").count());
        return result;
    }
}