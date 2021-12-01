package vb.javaCamp.pharmagator.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import vb.javaCamp.pharmagator.DTOs.PriceIdDTO;
import vb.javaCamp.pharmagator.entities.PriceId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class PriceMapperTest {

    @Test
    void mapTo_PriceIdDTO() {

        PriceId priceId = createPriceId();
        PriceIdDTO priceIdDTO = PriceMapper.entityToDto(priceId);

        assertEquals(priceId.getMedicineId(), priceIdDTO.getMedicineId());
        assertEquals(priceId.getPharmacyId(), priceIdDTO.getPharmacyId());

    }

    @Test
    void mapTo_PriceId() {

        PriceIdDTO priceIdDTO = createPriceIdDTO();
        PriceId priceId = PriceMapper.DtoToEntity(priceIdDTO);

        assertEquals(priceIdDTO.getMedicineId(), priceId.getMedicineId());
        assertEquals(priceIdDTO.getPharmacyId(), priceId.getPharmacyId());

    }

    private PriceId createPriceId() {

        PriceId priceId = new PriceId();
        priceId.setPharmacyId(1L);
        priceId.setMedicineId(2L);
        return priceId;

    }

    private PriceIdDTO createPriceIdDTO(){

        PriceIdDTO priceIdDTO = new PriceIdDTO();
        priceIdDTO.setPharmacyId(1L);
        priceIdDTO.setMedicineId(2L);
        return priceIdDTO;

    }
}
