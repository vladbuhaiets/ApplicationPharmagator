package vb.javaCamp.pharmagator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.entities.Medicine;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.repositories.MedicineRepository;
import vb.javaCamp.pharmagator.repositories.PharmacyRepository;
import vb.javaCamp.pharmagator.repositories.PriceRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SavingServiceImplTest {

    @InjectMocks
    SavingServiceImpl savingService;

    @Mock
    PharmacyRepository pharmacyRepository;

    @Mock
    PriceRepository priceRepository;

    @Mock
    MedicineRepository medicineRepository;


    @Test
    void saveToDB_whenEntitiesExists() {

        MedicineDTO medicineDTO = MedicineDTO.builder()
                .title("MedicineTest")
                .price(new BigDecimal(25))
                .externalId("externalIdTest")
                .pharmacyName("PharmacyNameTest")
                .build();

        Pharmacy pharmacy = new Pharmacy(20211206L, "PharmacyNameTest", "LinkTest");
        Medicine medicine = new Medicine(20211206L, "MedicineTitleTest");
        Price price = new Price(20211206L, 20211206L, new BigDecimal(25), "externalIdTest", Instant.now());

        when(pharmacyRepository.findByName(anyString())).thenReturn(Optional.of(pharmacy));
        when(medicineRepository.findByTitle(anyString())).thenReturn(Optional.of(medicine));
        when(priceRepository.save(any())).thenReturn(price);

        savingService.saveToDB(medicineDTO);

        verify(pharmacyRepository, times(1)).findByName(any());
        verify(medicineRepository, times(1)).findByTitle(any());
        verify(priceRepository, times(1)).save(any());
        verify(pharmacyRepository, times(0)).save(any());
        verify(medicineRepository, times(0)).save(any());

    }

    @Test
    void saveToDB_whenDBEmpty() {

        MedicineDTO medicineDTO = MedicineDTO.builder()
                .title("MedicineTest")
                .price(new BigDecimal(25))
                .externalId("externalIdTest")
                .pharmacyName("PharmacyNameTest")
                .build();

        Pharmacy pharmacy = new Pharmacy(20211206L, "SavePharmacyNameTest", "SaveLinkTest");
        Medicine medicine = new Medicine(20211206L, "SaveMedicineTitleTest");
        Price price = new Price(20211206L, 20211206L, new BigDecimal(25), "SaveexternalIdTest", Instant.now());

        when(pharmacyRepository.save(any())).thenReturn(pharmacy);
        when(medicineRepository.save(any())).thenReturn(medicine);
        when(priceRepository.save(any())).thenReturn(price);

        savingService.saveToDB(medicineDTO);

        verify(pharmacyRepository, times(1)).findByName(any());
        verify(medicineRepository, times(1)).findByTitle(any());
        verify(priceRepository, times(1)).save(any());
        verify(pharmacyRepository, times(1)).save(any());
        verify(medicineRepository, times(1)).save(any());

    }
}