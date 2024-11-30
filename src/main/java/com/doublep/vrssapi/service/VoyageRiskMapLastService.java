package com.doublep.vrssapi.service;

import com.doublep.vrssapi.mapper.VoyageRiskMapLastMapper;
import com.doublep.vrssapi.model.ShipVoyageRiskMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoyageRiskMapLastService {

    private final VoyageRiskMapLastMapper voyageRiskMapLastMapper;

    public List<ShipVoyageRiskMap> selectVoyageRiskMapLastList(List<List<Double>> coordinates) {
        return voyageRiskMapLastMapper.selectVoyageRiskMapLastList(coordinates);
    }
}
