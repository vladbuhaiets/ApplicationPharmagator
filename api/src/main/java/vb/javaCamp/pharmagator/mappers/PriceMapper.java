package vb.javaCamp.pharmagator.mappers;

import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.DTOs.PriceIdDTO;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.entities.PriceId;

public class PriceMapper {

    public static Price DtoToEntity(PriceDTO priceDTO) {

        Price price = new Price();
        price.setPrice(priceDTO.getPrice());
        price.setMedicineId(priceDTO.getMedicineId());
        price.setExternalId(priceDTO.getExternalId());
        price.setPharmacyId(priceDTO.getPharmacyId());
        price.setUpdatedAt(priceDTO.getUpdatedAt());
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
        priceDTO.setPrice(price.getPrice());
        priceDTO.setExternalId(price.getExternalId());
        priceDTO.setUpdatedAt(price.getUpdatedAt());
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