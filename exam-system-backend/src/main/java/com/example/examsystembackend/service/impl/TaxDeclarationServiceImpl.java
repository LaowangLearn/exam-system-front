package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.TaxDeclaration;
import com.example.examsystembackend.mapper.TaxDeclarationMapper;
import com.example.examsystembackend.service.TaxDeclarationService;
import org.springframework.stereotype.Service;

@Service
public class TaxDeclarationServiceImpl extends ServiceImpl<TaxDeclarationMapper, TaxDeclaration> implements TaxDeclarationService {
}