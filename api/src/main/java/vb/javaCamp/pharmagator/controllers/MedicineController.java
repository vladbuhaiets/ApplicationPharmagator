package vb.javaCamp.pharmagator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.services.MedicineService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    public List<MedicineDTO> getAllMedicines() {

        return medicineService.getAllMedicines();

    }

    @GetMapping("/{id}")
    public MedicineDTO getMedicine(@PathVariable("id") Long id) {

        return medicineService.getMedicine(id);

    }

    @PostMapping
    public MedicineDTO createMedicine(@Valid @RequestBody MedicineDTO medicineDTO) {

        return medicineService.createMedicine(medicineDTO);

    }

    @PutMapping("/{id}")
    public MedicineDTO updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO, @PathVariable("id") Long id) {

        return medicineService.updateMedicine(medicineDTO, id);

    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable("id") Long id){

        medicineService.deleteMedicine(id);

    }

}
