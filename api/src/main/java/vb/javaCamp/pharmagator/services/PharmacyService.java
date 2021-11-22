package vb.javaCamp.pharmagator.services;

import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;

import java.util.List;

public interface PharmacyService {

    List<PharmacyDTO> getAllPharmacies();

    PharmacyDTO getPharmacy(Long id);

    PharmacyDTO createPharmacy(PharmacyDTO pharmacyDTO);

    void deletePharmacy(Long id);

    PharmacyDTO updatePharmacy(PharmacyDTO pharmacyDTO, Long id);

}
