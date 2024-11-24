package com.doublep.vrssapi.service;


import com.doublep.vrssapi.mapper.AisFileCollectHistMapper;
import com.doublep.vrssapi.model.AisFileCollectHist;
import com.doublep.vrssapi.model.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AisFileCollectHistService {

    private final AisFileCollectHistMapper aisFileCollectHistMapper;

    public int countAisFileCollectHistList() {
        return aisFileCollectHistMapper.countAisFileCollectHistList();
    }

    public List<AisFileCollectHist> getAisFileCollectHistList(CommonRequest commonRequest) {
        return aisFileCollectHistMapper.selectAisFileCollectHistList(commonRequest);
    }

    @Transactional
    public int insertAisFileCollectHistList(AisFileCollectHist aisFileCollectHist) {
        return aisFileCollectHistMapper.insertAisFileCollectHist(aisFileCollectHist);
    }

}
