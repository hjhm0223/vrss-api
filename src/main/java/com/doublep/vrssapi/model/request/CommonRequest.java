package com.doublep.vrssapi.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonRequest {

    @Builder.Default
    private int offset = 0;
    @Builder.Default
    private int limit = 1000;

}
