package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.BankTransaction;
import com.example.examsystembackend.mapper.BankTransactionMapper;
import com.example.examsystembackend.service.BankTransactionService;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionServiceImpl extends ServiceImpl<BankTransactionMapper, BankTransaction> implements BankTransactionService {
}