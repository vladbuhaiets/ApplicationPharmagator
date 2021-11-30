package vb.javaCamp.pharmagator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.projections.PharmacyProjection;
import vb.javaCamp.pharmagator.services.PharmacyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping
    public List<PharmacyDTO> getAllPharmacies() {

        return pharmacyService.getAllPharmacies();

    }

    @GetMapping("/{id}")
    public PharmacyDTO getPharmacy(@PathVariable("id") Long id) {

        return pharmacyService.getPharmacy(id);

    }

    @PostMapping
    public PharmacyDTO createPharmacy(@Valid @RequestBody PharmacyDTO pharmacyDTO) {

        return pharmacyService.createPharmacy(pharmacyDTO);

    }

    @PutMapping("/{id}")
    public PharmacyDTO updatePharmacy(@Valid @RequestBody PharmacyDTO pharmacyDTO, @PathVariable("id") Long id) {

        return pharmacyService.updatePharmacy(pharmacyDTO, id);

    }

    @DeleteMapping("/{id}")
    public void deletePharmacy(@PathVariable("id") Long id) {

        pharmacyService.deletePharmacy(id);

    }

}
