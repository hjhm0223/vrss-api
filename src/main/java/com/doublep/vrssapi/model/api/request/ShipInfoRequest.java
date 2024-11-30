package com.doublep.vrssapi.model.api.request;

import com.doublep.vrssapi.constant.AisSource;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShipInfoRequest {

    private String keyword;
    private String aisSource;

}
