package vb.javaCamp.pharmagator.mappers;

import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.entities.Pharmacy;

public class PharmacyMapper {

    public static PharmacyDTO entityToDto(Pharmacy pharmacy) {

        PharmacyDTO dto = new PharmacyDTO();
        dto.setId(pharmacy.getId());
        dto.setName(pharmacy.getName());
        dto.setMedicineLinkTemplate(pharmacy.getMedicineLinkTemplate());
        return dto;

    }

}
