package com.doublep.vrssapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final AisShipFileService aisShipFileService;
    private final ShipService shipService;

    /**
     * 10분마다 실행
     */
    @Scheduled(fixedRate = 600000) // 10분
    public void monitorCsvFileFolder() {
        log.info("Checking for new files.");
        aisShipFileService.checkForNewFilesAndProcess();
    }

    /**
     * 10분마다 실행
     */
    @Scheduled(fixedRate = 600000) // 10분
    public void monitorUnregisteredShip() {
        log.info("Checking for unregistered ship data.");
        shipService.checkUnregisteredShipList();
    }
}
