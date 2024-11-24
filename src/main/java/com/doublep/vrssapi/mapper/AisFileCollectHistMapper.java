package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.AisFileCollectHist;
import com.doublep.vrssapi.model.CommonRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AisFileCollectHistMapper {

    int countAisFileCollectHistList();
    List<AisFileCollectHist> selectAisFileCollectHistList(CommonRequest request);
    int insertAisFileCollectHist(AisFileCollectHist aisFileCollectHist);

}
