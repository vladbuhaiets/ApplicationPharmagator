package vb.javaCamp.pharmagator.services;

import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.DTOs.PriceDTO;

import java.util.List;

public interface MedicineService {

    List<MedicineDTO> getAllMedicines();

    MedicineDTO getMedicine(Long id);

    MedicineDTO createMedicine(MedicineDTO medicineDTO);

    void deleteMedicine(Long id);

    MedicineDTO updateMedicine(MedicineDTO medicineDTO, Long id);

    List<PriceDTO> findPricesById(Long id);

}
