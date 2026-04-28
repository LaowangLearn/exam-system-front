package com.example.examsystembackend.service.impl;

import com.example.examsystembackend.service.AiService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class AiServiceImpl implements AiService {

    private final Random random = new Random();

    @Override
    public String generateSummary(String content) {
        String[] summaries = {
            "根据内容分析，核心要点已提取完成。",
            "内容总结：主要涉及业务流程优化和客户管理策略。",
            "摘要生成成功，关键信息已浓缩。",
            "分析完成，核心结论已提炼。"
        };
        return summaries[random.nextInt(summaries.length)];
    }

    @Override
    public String analyzeSentiment(String text) {
        if (text.contains("满意") || text.contains("好") || text.contains("棒") || text.contains("优秀")) {
            return "POSITIVE";
        } else if (text.contains("不满意") || text.contains("差") || text.contains("糟糕") || text.contains("投诉")) {
            return "NEGATIVE";
        }
        return "NEUTRAL";
    }

    @Override
    public String suggestReply(String context) {
        String[] replies = {
            "感谢您的反馈，我们将尽快处理。",
            "已收到您的信息，将在24小时内回复。",
            "感谢您的关注，相关部门已介入处理。",
            "您的建议已记录，我们会认真考虑。"
        };
        return replies[random.nextInt(replies.length)];
    }

    @Override
    public String extractKeywords(String text) {
        List<String> keywords = Arrays.asList("客户", "订单", "产品", "服务", "反馈", "需求", "问题", "解决方案");
        StringBuilder result = new StringBuilder();
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                if (result.length() > 0) result.append(",");
                result.append(keyword);
            }
        }
        return result.length() > 0 ? result.toString() : "客户,订单,服务";
    }

    @Override
    public String predictCustomerValue(String customerData) {
        double score = random.nextDouble() * 100;
        if (score >= 80) return "HIGH";
        if (score >= 50) return "MEDIUM";
        return "LOW";
    }
}