package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.AisFileCollectHist;
import com.doublep.vrssapi.model.request.CommonRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AisFileCollectHistMapper {

    int countNotFailedAisFileCollectHistList();
    List<AisFileCollectHist> getNotFailedAisFileCollectHistList(CommonRequest request);
    int insertAisFileCollectHist(AisFileCollectHist aisFileCollectHist);
    int updateAisFileCollectHist(AisFileCollectHist aisFileCollectHist);

}
