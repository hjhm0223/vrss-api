package com.doublep.kcgsasystem.service;

import com.doublep.kcgsasystem.mapper.ShipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipService {

    private final ShipMapper shipMapper;

    public List<Object> getShipList() {
        // latitude 25.00 ~ 45.00
        // longitude 120.00 ~ 140.00

        // AIS NOW 선박 조회
        // 조건: 위경도, 시간
        // 선박 종류, 속도, 방향 포함

        return null;
    }

    public Object getShipInfo(String shipId) {
        // 시스템 내 선박 조회
        // SV 선박 조회
        return null;
    }

    public List<Object> getShipPastTrackList(String shipId) {
        // SV 선박 조회 /search param:imoNo

        // 시스템 내 미등록 선박 체크 -> 미등록 선박일 경우 등록

        // SV Fleet 등록

        // 과거 궤적 조회 (60일)

        // 이력, 현재 상태 적재

        // 포트콜 조회

        // 도착지

        return null;
    }
}
