package vb.javaCamp.pharmagator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.entities.Medicine;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.repositories.MedicineRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @InjectMocks
    MedicineServiceImpl medicineService;

    @Mock
    MedicineRepository medicineRepository;

    @Test
    void getAllMedicines() {

        List<Medicine> list = new ArrayList<>();

        list.add(new Medicine(20213011L, "title1"));
        list.add(new Medicine(20213012L, "title2"));

        when(medicineRepository.findAll()).thenReturn(list);

        List<MedicineDTO> empList = medicineService.getAllMedicines();

        assertEquals(2, empList.size());
        assertEquals("title1", empList.get(0).getTitle());

    }

    @Test
    void getMedicine() {

        Long id = 20213011L;
        Medicine testMedicine = new Medicine(id, "title");

        when(medicineRepository.findById(any())).thenReturn(Optional.of(testMedicine));

        MedicineDTO dto = medicineService.getMedicine(id);

        assertNotNull(dto);
        assertEquals("title", dto.getTitle());

    }

    @Test
    void createMedicine() {

        Long id = 20213011L;

        Medicine testMedicine = new Medicine(id, "title");
        MedicineDTO medicineDTO = new MedicineDTO();
        medicineDTO.setTitle("title");

        when(medicineRepository.save(any(Medicine.class))).thenReturn(testMedicine);

        MedicineDTO dto = medicineService.createMedicine(medicineDTO);

        assertEquals("title", dto.getTitle());
        verify(medicineRepository, times(1)).save(any());

    }

    @Test
    void deleteMedicine() {

        Long id = 20213011L;

        medicineService.deleteMedicine(id);
        medicineService.deleteMedicine(id);

        verify(medicineRepository, times(2)).deleteById(any());

    }

    @Test
    void updateMedicine() {

        Long id = 20213011L;

        Medicine testMedicine = new Medicine(id, "newTitle");
        MedicineDTO medicineDTO = new MedicineDTO();
        medicineDTO.setTitle("newTitle");

        when(medicineRepository.save(any(Medicine.class))).thenReturn(testMedicine);

        MedicineDTO dto = medicineService.updateMedicine(medicineDTO, id);

        assertNotEquals("title", dto.getTitle());
        assertEquals("newTitle", dto.getTitle());
        verify(medicineRepository, times(1)).save(any());


    }
}