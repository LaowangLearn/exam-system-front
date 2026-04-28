package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Customer;
import com.example.examsystembackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crm/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public IPage<Customer> list(@RequestParam(defaultValue = "1") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        return customerService.page(new Page<>(page, size));
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        customer.setCustomerCode(customerService.generateCustomerCode());
        customer.setCreateTime(LocalDateTime.now());
        customerService.save(customer);
        return customer;
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        customer.setUpdateTime(LocalDateTime.now());
        customerService.updateById(customer);
        return customer;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return customerService.removeById(id);
    }

    @GetMapping("/search")
    public List<Customer> search(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String industry,
                                  @RequestParam(required = false) String level,
                                  @RequestParam(required = false) String status) {
        return customerService.search(name, industry, level, status);
    }

    @GetMapping("/statistics")
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", customerService.count());
        result.put("active", customerService.lambdaQuery().eq(Customer::getStatus, "ACTIVE").count());
        result.put("potential", customerService.lambdaQuery().eq(Customer::getStatus, "POTENTIAL").count());
        return result;
    }
}