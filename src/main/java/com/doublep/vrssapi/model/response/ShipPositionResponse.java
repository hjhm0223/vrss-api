package com.doublep.vrssapi.model.response;

import com.doublep.vrssapi.model.AisShipData;
import com.doublep.vrssapi.model.Ship;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShipPositionResponse extends Ship {

    private AisShipData position;

}
