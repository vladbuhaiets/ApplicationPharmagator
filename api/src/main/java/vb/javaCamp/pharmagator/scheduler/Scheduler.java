package vb.javaCamp.pharmagator.scheduler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.dataProviders.DataProvider;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Scheduler {

    private DataProvider dataProvider;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void schedule() {

        log.info("Scheduler start at {}", Instant.now());
        dataProvider.loadData().forEach(this::storeToDatabase);

    }

    private void storeToDatabase(MedicineDTO dto) {



    }

}
