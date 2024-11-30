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

    private Integer fileCollectSeq;
    private String fileName;
    private int totalCount;
    private String status;
    private LocalDateTime startTimestamp;
    private LocalDateTime endTimestamp;
    private long duration;

}
