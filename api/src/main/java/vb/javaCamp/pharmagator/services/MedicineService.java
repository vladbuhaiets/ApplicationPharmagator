package vb.javaCamp.pharmagator.services;

import vb.javaCamp.pharmagator.DTOs.MedicineDTO;

import java.util.List;

public interface MedicineService {

    List<MedicineDTO> getAllPharmacies();

    MedicineDTO getMedicine(Long id);

    MedicineDTO createMedicine(MedicineDTO pharmacyDTO);

    void deleteMedicine(Long id);

    MedicineDTO updateMedicine(MedicineDTO pharmacyDTO, Long id);

}
