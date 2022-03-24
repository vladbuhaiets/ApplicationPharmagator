package vb.javaCamp.pharmagator.mappers;

import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.entities.Pharmacy;

public class PharmacyMapper {

    private PharmacyMapper() {
    }

    public static PharmacyDTO entityToDto(Pharmacy pharmacy) {

        PharmacyDTO dto = new PharmacyDTO();
        dto.setName(pharmacy.getName());
        dto.setMedicineLinkTemplate(pharmacy.getMedicineLinkTemplate());
        return dto;

    }

    public static Pharmacy DtoToEntity(PharmacyDTO pharmacyDTO) {

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(pharmacyDTO.getName());
        pharmacy.setMedicineLinkTemplate(pharmacyDTO.getMedicineLinkTemplate());
        return pharmacy;

    }
}
