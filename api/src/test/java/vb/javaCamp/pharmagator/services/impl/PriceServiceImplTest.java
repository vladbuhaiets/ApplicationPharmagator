package vb.javaCamp.pharmagator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.repositories.PriceRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @InjectMocks
    PriceServiceImpl priceService;

    @Mock
    PriceRepository priceRepository;

    @Test
    void getAllPrices() {

        List<Price> list = new ArrayList<>();

        list.add(new Price(20213011L, 20213011L, BigDecimal.valueOf(20213011L), "str", Instant.now()));
        list.add(new Price(20213012L, 20213012L, BigDecimal.valueOf(20213011L), "str", Instant.now()));

        when(priceRepository.findAll()).thenReturn(list);

        List<PriceDTO> dtos = priceService.getAllPrices();

        assertEquals(2, dtos.size());
        assertEquals(20213011L, dtos.get(0).getPharmacyId());

    }

    @Test
    void getPrice() {

        Price price = new Price(20213011L, 20213011L, BigDecimal.valueOf(20213011L), "str", Instant.now());

        when(priceRepository.findById(any())).thenReturn(Optional.of(price));

        PriceDTO dto = priceService.getPrice(20213011L, 20213011L);

        assertEquals(20213011L, dto.getPharmacyId());

    }

    @Test
    void createPrice() {

        Price price = new Price(20213011L, 20213011L, BigDecimal.valueOf(20213011L), "str", Instant.now());
        PriceDTO dto = new PriceDTO(20213011L, 20213011L, BigDecimal.valueOf(20213011L), "str");

        when(priceRepository.save(any(Price.class))).thenReturn(price);

        PriceDTO created = priceService.createPrice(dto);

        assertEquals(20213011L, dto.getPharmacyId());
        verify(priceRepository, times(1)).save(any());

    }

    @Test
    void updatePrice() {

        Long pid = 20213012L;
        Long mid = 20213012L;

        Price price = new Price(pid, mid, BigDecimal.valueOf(20213012L), "str", Instant.now());
        PriceDTO dto = new PriceDTO(pid, mid, BigDecimal.valueOf(20213012L), "str");

        when(priceRepository.save(any(Price.class))).thenReturn(price);

        PriceDTO created = priceService.updatePrice(dto,pid,mid);

        assertNotEquals(20213011L, dto.getPharmacyId());
        assertEquals(20213012L, dto.getPharmacyId());
        verify(priceRepository, times(1)).save(any());

    }

    @Test
    void deletePrice() {

        Long pid = 20213011L;
        Long mid = 20213011L;

        priceService.deletePrice(pid, mid);
        priceService.deletePrice(pid, mid);

        verify(priceRepository, times(2)).deleteById(any());

    }

}