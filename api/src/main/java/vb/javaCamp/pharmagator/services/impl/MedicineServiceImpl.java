package vb.javaCamp.pharmagator.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.entities.Medicine;
import vb.javaCamp.pharmagator.mappers.MedicineMapper;
import vb.javaCamp.pharmagator.mappers.PriceMapper;
import vb.javaCamp.pharmagator.repositories.MedicineRepository;
import vb.javaCamp.pharmagator.repositories.PriceRepository;
import vb.javaCamp.pharmagator.services.MedicineService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final PriceRepository priceRepository;

    public List<MedicineDTO> getAllMedicines() {

        return medicineRepository.findAll()
                .stream()
                .map(MedicineMapper::entityToDto)
                .toList();

    }

    public MedicineDTO getMedicine(Long id) {

        return medicineRepository.findById(id)
                .map(MedicineMapper::entityToDto)
                .orElse(new MedicineDTO());

    }

    public MedicineDTO createMedicine(MedicineDTO medicineDTO) {

        Medicine medicine = MedicineMapper.DtoToMedicineEntity(medicineDTO);
        Medicine created = medicineRepository.save(medicine);
        return MedicineMapper.entityToDto(created);

    }

    public void deleteMedicine(Long id) {

        medicineRepository.deleteById(id);

    }

    public MedicineDTO updateMedicine(MedicineDTO medicineDTO, Long id) {

        Medicine medicine = MedicineMapper.DtoToMedicineEntity(medicineDTO);
        medicine.setId(id);
        Medicine updated = medicineRepository.save(medicine);
        return MedicineMapper.entityToDto(updated);

    }

    @Override
    public List<PriceDTO> findPricesById(Long id) {
        return priceRepository.findAllByMedicineId(id).stream()
                .map(PriceMapper::entityToDto)
                .toList();
    }

}
