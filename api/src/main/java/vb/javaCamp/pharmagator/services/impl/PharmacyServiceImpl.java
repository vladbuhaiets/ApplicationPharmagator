package vb.javaCamp.pharmagator.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.mappers.PharmacyMapper;
import vb.javaCamp.pharmagator.projections.PharmacyProjection;
import vb.javaCamp.pharmagator.repositories.PharmacyRepository;
import vb.javaCamp.pharmagator.services.PharmacyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    public List<PharmacyDTO> getAllPharmacies() {

        return pharmacyRepository.findAll()
                .stream()
                .map(PharmacyMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public PharmacyDTO getPharmacy(Long id) {

        return pharmacyRepository.findById(id)
                .map(PharmacyMapper::entityToDto)
                .orElse(new PharmacyDTO());

    }

    public PharmacyDTO createPharmacy(PharmacyDTO pharmacyDTO) {

        Pharmacy pharmacy = PharmacyMapper.DtoToEntity(pharmacyDTO);
        Pharmacy createdPharmacy = pharmacyRepository.save(pharmacy);
        PharmacyDTO dto = PharmacyMapper.entityToDto(createdPharmacy);
        return dto;

    }

    public void deletePharmacy(Long id) {

        pharmacyRepository.deleteById(id);

    }

    public PharmacyDTO updatePharmacy(PharmacyDTO pharmacyDTO, Long id) {

        Pharmacy pharmacy = PharmacyMapper.DtoToEntity(pharmacyDTO);
        pharmacy.setId(id);
        Pharmacy createdPharmacy = pharmacyRepository.save(pharmacy);
        PharmacyDTO dto = PharmacyMapper.entityToDto(createdPharmacy);
        return dto;

    }

}
