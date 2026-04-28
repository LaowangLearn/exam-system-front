package com.example.examsystembackend.gov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gov")
public class GovSystemProperties {

    private String gmEnabled = "true";
    private String aiEnabled = "true";
    private String aiModelProvider = "local";
    private String dataPath = "/data/gov";
    private String filePath = "/data/gov/files";
    private Integer passwordExpireDays = 90;
    private Integer maxLoginFailCount = 5;
    private Integer loginFailLockMinutes = 30;

    public String getGmEnabled() { return gmEnabled; }
    public void setGmEnabled(String gmEnabled) { this.gmEnabled = gmEnabled; }
    public String getAiEnabled() { return aiEnabled; }
    public void setAiEnabled(String aiEnabled) { this.aiEnabled = aiEnabled; }
    public String getAiModelProvider() { return aiModelProvider; }
    public void setAiModelProvider(String aiModelProvider) { this.aiModelProvider = aiModelProvider; }
    public String getDataPath() { return dataPath; }
    public void setDataPath(String dataPath) { this.dataPath = dataPath; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public Integer getPasswordExpireDays() { return passwordExpireDays; }
    public void setPasswordExpireDays(Integer passwordExpireDays) { this.passwordExpireDays = passwordExpireDays; }
    public Integer getMaxLoginFailCount() { return maxLoginFailCount; }
    public void setMaxLoginFailCount(Integer maxLoginFailCount) { this.maxLoginFailCount = maxLoginFailCount; }
    public Integer getLoginFailLockMinutes() { return loginFailLockMinutes; }
    public void setLoginFailLockMinutes(Integer loginFailLockMinutes) { this.loginFailLockMinutes = loginFailLockMinutes; }
}
