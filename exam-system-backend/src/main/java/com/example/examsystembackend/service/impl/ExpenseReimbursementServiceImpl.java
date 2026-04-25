package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.ExpenseReimbursement;
import com.example.examsystembackend.mapper.ExpenseReimbursementMapper;
import com.example.examsystembackend.service.ExpenseReimbursementService;
import org.springframework.stereotype.Service;

@Service
public class ExpenseReimbursementServiceImpl extends ServiceImpl<ExpenseReimbursementMapper, ExpenseReimbursement> implements ExpenseReimbursementService {
}