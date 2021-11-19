package vb.javaCamp.pharmagator.services;

import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;

import java.util.List;
import java.util.Optional;

public interface PharmacyService {

    List<PharmacyDTO> getAllPharmacies();

    PharmacyDTO getPharmacy(Long id);

    PharmacyDTO createPharmacy(PharmacyDTO pharmacyDTO);

    void deletePharmacy(Long id);

    PharmacyDTO updatePharmacy(PharmacyDTO pharmacyDTO, Long id);

}
