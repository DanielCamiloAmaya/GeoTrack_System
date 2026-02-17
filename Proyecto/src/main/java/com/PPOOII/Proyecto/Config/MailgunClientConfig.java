package com.PPOOII.Proyecto.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MailgunClientConfig {

@Bean
public WebClient mailgunWebClient(MailgunProperties props) {
    return WebClient.builder()
    .baseUrl("https://api.mailgun.net/v3/" + props.getDomain())
    .defaultHeaders(h -> h.setBasicAuth("api", props.getApiKey()))
    .build();
}
}