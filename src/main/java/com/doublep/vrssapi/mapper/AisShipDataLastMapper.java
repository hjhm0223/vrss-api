package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.AisShipData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AisShipDataLastMapper {

    int upsertAisShipDataLast(AisShipData aisShipDataList);

}
