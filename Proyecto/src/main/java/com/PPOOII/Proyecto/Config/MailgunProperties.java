package com.PPOOII.Proyecto.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mailgun")
public class MailgunProperties {
    private String apiKey;
    private String domain;

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
}