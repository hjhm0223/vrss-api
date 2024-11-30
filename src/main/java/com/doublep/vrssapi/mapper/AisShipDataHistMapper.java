package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.AisShipData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AisShipDataHistMapper {

    int insertAisShipDataHist(AisShipData aisShipData);
    int bulkInsertAisShipDataHist(List<AisShipData> aisShipDataList);

}
