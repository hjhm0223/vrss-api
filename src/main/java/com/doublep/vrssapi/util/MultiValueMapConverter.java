package com.doublep.vrssapi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultiValueMapConverter {

    private final ObjectMapper objectMapper0;
    private static ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = this.objectMapper0;
    }

    public static MultiValueMap<String, String> convert(Object dto) {
        try {
            var result = new LinkedMultiValueMap<String, String>();
            var dtoMap = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});
            result.setAll(dtoMap);

            return result;
        } catch (Exception e) {
            log.error("Parameter 변환 중 오류가 발생했습니다. parameter={} error={}", dto, e.getLocalizedMessage());
            throw new IllegalStateException("Parameter 변환 중 오류가 발생했습니다.");
        }
    }
}

