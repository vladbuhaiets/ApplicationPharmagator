package vb.javaCamp.pharmagator.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.entities.Price;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class MedicineMapperTest {

    @Test
    void DtoToPriceEntity() {

        MedicineDTO medicineDTO = new MedicineDTO(2L,"title", BigDecimal.valueOf(2L), "externalId", "pharmacyName");
        Price price = MedicineMapper.DtoToPriceEntity(medicineDTO);

        assertEquals(medicineDTO.getPrice(), price.getMedicineId());
        assertEquals(medicineDTO.getExternalId(), price.getExternalId());

    }

}