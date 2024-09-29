package com.doublep.vrssapi.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SvmpUrlConstant {

    public static final String SVMP_API_SHIP_POSITION = "/api/v1/ship/position";
    public static final String SVMP_API_SHIP = "/api/v1/ship/search";
    public static final String SVMP_API_SHIP_PAST_TRACK = "/api/v1/ship/{shipId}/pastTrack";
    public static final String SVMP_API_SHIP_LAST_PAST_TRACK = "/api/v1/ship/{shipId}/pastTrack/fromLastPort";
    public static final String SVMP_API_SHIP_PREDICT_TRACK = "/api/v1/route";

}
