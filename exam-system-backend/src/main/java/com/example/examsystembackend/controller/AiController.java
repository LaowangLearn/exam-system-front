package com.example.examsystembackend.controller;

import com.example.examsystembackend.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/summary")
    public Map<String, Object> generateSummary(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        result.put("summary", aiService.generateSummary(request.get("content")));
        return result;
    }

    @PostMapping("/sentiment")
    public Map<String, Object> analyzeSentiment(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        result.put("sentiment", aiService.analyzeSentiment(request.get("text")));
        return result;
    }

    @PostMapping("/reply")
    public Map<String, Object> suggestReply(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        result.put("reply", aiService.suggestReply(request.get("context")));
        return result;
    }

    @PostMapping("/keywords")
    public Map<String, Object> extractKeywords(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        result.put("keywords", aiService.extractKeywords(request.get("text")));
        return result;
    }

    @PostMapping("/predict-customer")
    public Map<String, Object> predictCustomerValue(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        result.put("valueLevel", aiService.predictCustomerValue(request.get("customerData")));
        return result;
    }
}