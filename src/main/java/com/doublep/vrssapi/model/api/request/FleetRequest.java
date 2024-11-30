package com.doublep.vrssapi.model.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FleetRequest {

    private List<String> shipIds;

}
