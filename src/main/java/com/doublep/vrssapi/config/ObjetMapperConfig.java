package com.doublep.vrssapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ObjetMapperConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

}
