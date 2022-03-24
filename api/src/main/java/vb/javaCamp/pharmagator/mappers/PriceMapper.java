package vb.javaCamp.pharmagator.mappers;

import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.DTOs.PriceIdDTO;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.entities.PriceId;

import java.time.Instant;

public class PriceMapper {

    private PriceMapper() {
    }

    public static Price DtoToEntity(PriceDTO priceDTO) {

        Price price = new Price();
        price.setMedicinePrice(priceDTO.getPrice());
        price.setMedicineId(priceDTO.getMedicineId());
        price.setExternalId(priceDTO.getExternalId());
        price.setPharmacyId(priceDTO.getPharmacyId());
        price.setUpdatedAt(Instant.now());
        return price;

    }

    public static PriceId DtoToEntity(PriceIdDTO priceIdDTO) {

        PriceId priceId = new PriceId();
        priceId.setMedicineId(priceIdDTO.getMedicineId());
        priceId.setPharmacyId(priceIdDTO.getPharmacyId());
        return priceId;

    }

    public static PriceDTO entityToDto(Price price) {

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setPrice(price.getMedicinePrice());
        priceDTO.setExternalId(price.getExternalId());
        priceDTO.setMedicineId(price.getMedicineId());
        priceDTO.setPharmacyId(price.getPharmacyId());
        return priceDTO;

    }

    public static PriceIdDTO entityToDto(PriceId price) {

        PriceIdDTO dto = new PriceIdDTO();
        dto.setMedicineId(price.getMedicineId());
        dto.setPharmacyId(price.getPharmacyId());
        return dto;

    }

}
