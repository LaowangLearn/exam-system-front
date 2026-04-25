package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.ExpenseItem;
import com.example.examsystembackend.mapper.ExpenseItemMapper;
import com.example.examsystembackend.service.ExpenseItemService;
import org.springframework.stereotype.Service;

@Service
public class ExpenseItemServiceImpl extends ServiceImpl<ExpenseItemMapper, ExpenseItem> implements ExpenseItemService {
}