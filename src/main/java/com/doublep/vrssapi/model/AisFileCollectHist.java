package com.doublep.vrssapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AisFileCollectHist {

    private String fileName;
    private LocalDateTime fileCollectTimestamp;

}
