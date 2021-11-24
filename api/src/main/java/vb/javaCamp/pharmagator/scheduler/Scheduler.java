package vb.javaCamp.pharmagator.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.dataProviders.DataProvider;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProviders;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void schedule() {

        log.info("Scheduler start at {}", Instant.now());
        dataProviders.parallelStream()
                .flatMap(DataProvider::loadData)
                .forEach(this::storeToDatabase);

    }

    private void storeToDatabase(MedicineDTO dto) {

        log.info(dto.getTitle() + " " + dto.getExternalId() + " " + dto.getPharmacyName() + " " + dto.getPrice());

    }

}
