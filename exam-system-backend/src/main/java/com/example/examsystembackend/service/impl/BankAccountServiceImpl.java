package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.BankAccount;
import com.example.examsystembackend.mapper.BankAccountMapper;
import com.example.examsystembackend.service.BankAccountService;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl extends ServiceImpl<BankAccountMapper, BankAccount> implements BankAccountService {
}