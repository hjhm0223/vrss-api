package com.doublep.vrssapi.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SvProcessStatus {
    public static final String UNREGISTERED = "0000";
    public static final String REGISTERED = "0100";
    public static final String REGISTRATION_NOT_REQUIRED = "0200";
    public static final String REGISTRATION_NO_DATA_ERROR = "0201";
}
