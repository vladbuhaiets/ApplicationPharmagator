package vb.javaCamp.pharmagator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.services.CsvParserService;
import vb.javaCamp.pharmagator.services.MedicineService;
import org.springframework.http.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;
    private final CsvParserService csvParserService;

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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        csvParserService.parseMultipartFile(file);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public MedicineDTO updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO, @PathVariable("id") Long id) {

        return medicineService.updateMedicine(medicineDTO, id);

    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable("id") Long id) {

        medicineService.deleteMedicine(id);

    }

}
