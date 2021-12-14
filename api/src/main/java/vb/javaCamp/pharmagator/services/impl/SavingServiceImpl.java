package vb.javaCamp.pharmagator.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.entities.Medicine;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.mappers.MedicineMapper;
import vb.javaCamp.pharmagator.repositories.MedicineRepository;
import vb.javaCamp.pharmagator.repositories.PharmacyRepository;
import vb.javaCamp.pharmagator.repositories.PriceRepository;
import vb.javaCamp.pharmagator.services.SavingService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final PharmacyRepository pharmacyRepository;

    private final MedicineRepository medicineRepository;

    private final PriceRepository priceRepository;

    @Override
    public void saveToDB(MedicineDTO medicineDTO) {

        Medicine medicine = MedicineMapper.DtoToMedicineEntity(medicineDTO);
        Price price = MedicineMapper.DtoToPriceEntity(medicineDTO);
        String pharmacyName = medicineDTO.getPharmacyName();
        String medicineTitle = medicineDTO.getTitle();

        Pharmacy pharmacyFromDB = new Pharmacy();

        if (pharmacyName != null && !pharmacyName.equals("")) {
            pharmacyFromDB = pharmacyRepository.findByName(medicineDTO.getPharmacyName())
                    .orElseGet(() -> {
                        Pharmacy pharmacy = new Pharmacy();
                        pharmacy.setName(medicineDTO.getPharmacyName());
                        return pharmacyRepository.save(pharmacy);
                    });
        }

        Medicine medicineFromDB = new Medicine();

        if (medicineTitle != null && !medicineTitle.equals("")) {
            medicineFromDB = medicineRepository.findByTitle(medicine.getTitle())
                    .orElseGet(() -> medicineRepository.save(medicine));
        }

        price.setPharmacyId(pharmacyFromDB.getId());
        price.setMedicineId(medicineFromDB.getId());
        price.setUpdatedAt(Instant.now());
        priceRepository.save(price);

    }
}
