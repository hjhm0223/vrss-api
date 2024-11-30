package com.doublep.vrssapi.config;

import com.doublep.vrssapi.advisor.exception.CustomSvmpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class RestClientConfig {

    @Value("${api.svmp.baseUrl}")
    private String svmpBaseUrl;
    @Value("${api.svmp.authCode}")
    private String svmpAuthCode;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(svmpBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + svmpAuthCode)
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new CustomSvmpException(response.getStatusCode().toString());
                })
                .build();
    }
}
