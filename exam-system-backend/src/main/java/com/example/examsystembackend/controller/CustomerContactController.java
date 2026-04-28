package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.CustomerContact;
import com.example.examsystembackend.service.CustomerContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/crm/contacts")
@RequiredArgsConstructor
public class CustomerContactController {

    private final CustomerContactService customerContactService;

    @GetMapping("/customer/{customerId}")
    public List<CustomerContact> getByCustomerId(@PathVariable Long customerId) {
        return customerContactService.getByCustomerId(customerId);
    }

    @GetMapping("/{id}")
    public CustomerContact getById(@PathVariable Long id) {
        return customerContactService.getById(id);
    }

    @PostMapping
    public CustomerContact create(@RequestBody CustomerContact contact) {
        contact.setCreateTime(LocalDateTime.now());
        customerContactService.save(contact);
        return contact;
    }

    @PutMapping("/{id}")
    public CustomerContact update(@PathVariable Long id, @RequestBody CustomerContact contact) {
        contact.setId(id);
        customerContactService.updateById(contact);
        return contact;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return customerContactService.removeById(id);
    }
}