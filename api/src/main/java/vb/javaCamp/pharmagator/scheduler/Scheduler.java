package vb.javaCamp.pharmagator.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.dataProviders.DataProvider;
import vb.javaCamp.pharmagator.services.SavingService;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProviders;
    private final SavingService savingService;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void schedule() {

        log.info("Scheduler start at {}", Instant.now());
        dataProviders.parallelStream()
                .flatMap(DataProvider::loadData)
                .forEach(this::storeToDatabase);

    }

    private void storeToDatabase(MedicineDTO dto) {

        log.info(dto.getTitle() + " " + dto.getExternalId() + " " + dto.getPharmacyName() + " " + dto.getPrice());
        savingService.saveToDB(dto);

    }

}
