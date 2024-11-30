package com.doublep.vrssapi.model.api.response;

import com.doublep.vrssapi.model.api.ShipInfo;
import com.doublep.vrssapi.model.api.ShipPortCall;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShipPortCallResponse {

    private List<ShipPortCall> portCalls;
    private ShipInfo ship;

}
