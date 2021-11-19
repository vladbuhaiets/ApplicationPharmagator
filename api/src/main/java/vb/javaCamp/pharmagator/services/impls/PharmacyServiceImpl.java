package vb.javaCamp.pharmagator.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.mappers.PharmacyMapper;
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
}
