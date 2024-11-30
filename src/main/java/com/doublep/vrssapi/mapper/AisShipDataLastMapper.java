package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.AisShipData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AisShipDataLastMapper {

    int upsertAisShipDataLast(AisShipData aisShipData);
    int bulkUpsertAisShipDataLast(List<AisShipData> aisShipDataList);

}
