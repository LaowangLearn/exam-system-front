package com.example.examsystembackend.service;

public interface AiService {
    String generateSummary(String content);
    String analyzeSentiment(String text);
    String suggestReply(String context);
    String extractKeywords(String text);
    String predictCustomerValue(String customerData);
}