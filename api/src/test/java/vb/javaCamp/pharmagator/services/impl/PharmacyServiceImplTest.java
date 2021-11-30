package vb.javaCamp.pharmagator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.repositories.PharmacyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PharmacyServiceImplTest {

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Test
    void getAllPharmacies() {

        List<Pharmacy> list = new ArrayList<>();

        list.add(new Pharmacy(1L, "test1", "link1"));
        list.add(new Pharmacy(2L, "test2", "link2"));

        when(pharmacyRepository.findAll()).thenReturn(list);

        List<PharmacyDTO> empList = pharmacyService.getAllPharmacies();

        assertEquals(2, empList.size());
        assertEquals("test1", empList.get(0).getName());

    }

    @Test
    void getPharmacy() {

        Long id = 20213011L;
        Pharmacy testPharmacy = new Pharmacy(id, "Gname", "link");

        when(pharmacyRepository.findById(any()))
                .thenReturn(Optional.of(testPharmacy));

        PharmacyDTO dto = pharmacyService.getPharmacy(any());

        assertEquals("Gname", dto.getName());

    }

    @Test
    void createPharmacy() {

        Long id = 20213011L;
        Pharmacy testPharmacy = new Pharmacy(id, "Cname", "link");
        PharmacyDTO testDto = new PharmacyDTO("Cname", "link");

        when(pharmacyRepository.save(any()))
                .thenReturn(testPharmacy);

        PharmacyDTO dto = pharmacyService.createPharmacy(testDto);

        assertEquals("Cname", dto.getName());
        verify(pharmacyRepository, times(1)).save(any());

    }

    @Test
    void deletePharmacy() {

        Long id = 20213011L;

        pharmacyService.deletePharmacy(id);
        pharmacyService.deletePharmacy(id);

        verify(pharmacyRepository, times(2)).deleteById(any());

    }

    @Test
    void updatePharmacy() {

        Long id = 20213011L;

        Pharmacy updated = new Pharmacy(id, "newName", "link");
        PharmacyDTO testDto = new PharmacyDTO("newName", "link");

        when(pharmacyRepository.save(any())).thenReturn(updated);

        PharmacyDTO dto = pharmacyService.updatePharmacy(testDto, id);

        assertNotEquals("Cname", dto.getName());
        assertEquals("newName", dto.getName());
        verify(pharmacyRepository, times(1)).save(any());

    }

}