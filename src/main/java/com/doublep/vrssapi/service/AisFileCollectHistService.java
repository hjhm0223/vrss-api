package com.doublep.vrssapi.service;


import com.doublep.vrssapi.mapper.AisFileCollectHistMapper;
import com.doublep.vrssapi.model.AisFileCollectHist;
import com.doublep.vrssapi.model.request.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AisFileCollectHistService {

    private final AisFileCollectHistMapper aisFileCollectHistMapper;

    public int countNotFailedAisFileCollectHistList() {
        return aisFileCollectHistMapper.countNotFailedAisFileCollectHistList();
    }

    public List<AisFileCollectHist> getNotFailedAisFileCollectHistList(CommonRequest commonRequest) {
        return aisFileCollectHistMapper.getNotFailedAisFileCollectHistList(commonRequest);
    }

    @Transactional
    public int insertAisFileCollectHistList(AisFileCollectHist aisFileCollectHist) {
        return aisFileCollectHistMapper.insertAisFileCollectHist(aisFileCollectHist);
    }

    @Transactional
    public int updateAisFileCollectHist(AisFileCollectHist aisFileCollectHist) {
        return aisFileCollectHistMapper.updateAisFileCollectHist(aisFileCollectHist);
    }

}
