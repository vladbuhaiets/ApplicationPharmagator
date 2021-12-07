package vb.javaCamp.pharmagator.services.impl;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.services.SavingService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CsvParserServiceImplTest {

    @InjectMocks
    CsvParserServiceImpl csvParserService;

    @Mock
    CsvParser csvParser;

    @Mock
    BeanListProcessor<MedicineDTO> rowProcessor;

    @Mock
    SavingService savingService;

    public static String TYPE = "text/csv";

    private final String NAME = "name";
    private final String PRICE = "price";
    private final String EXTERNAL = "external";
    private final String PHARMACY = "pharmacy";

    @SneakyThrows
    @Test
    void parseMultipartFile() {

        InputStream resource = new ClassPathResource(
                "medicine.csv").getInputStream();

        MultipartFile file = new MockMultipartFile("testFile", "originalName", TYPE, resource);

        List<MedicineDTO> dtos = List.of(
                new MedicineDTO("Medicine1", BigDecimal.valueOf(25.5), "ExId1", "Pharmacy1"),
                new MedicineDTO("Medicine2", BigDecimal.valueOf(80), "ExId2", "Pharmacy2"),
                new MedicineDTO("Medicine3", BigDecimal.valueOf(5.5), "ExId3", "Pharmacy3")
        );

        when(rowProcessor.getBeans()).thenReturn(dtos);

        doNothing().when(savingService).saveToDB(any());

        String actualMessage = csvParserService.parseMultipartFile(file);

        assertEquals("File : " + file.getOriginalFilename() + " is saved!", actualMessage);

    }

    @SneakyThrows
    @Test
    void parseMultipartFile_isEmpty() {

        InputStream resource = new ClassPathResource(
                "empty.csv").getInputStream();

        MultipartFile file = new MockMultipartFile("testFile", "originalName", TYPE, resource);

        String actualMessage = csvParserService.parseMultipartFile(file);

        assertEquals("File is empty!", actualMessage);

    }

    @SneakyThrows
    @Test
    void parseMultipartFile_IncorrectType() {

        InputStream resource = new ClassPathResource(
                "medicine.csv").getInputStream();

        MultipartFile file = new MockMultipartFile("testFile", "originalName", "word", resource);

        String actualMessage = csvParserService.parseMultipartFile(file);

        assertEquals("File has not correct type", actualMessage);

    }

    @SneakyThrows
    @Test
    void parseIS() {

        InputStream resource = new ClassPathResource(
                "medicine.csv").getInputStream();

        List<MedicineDTO> dtos = List.of(
                new MedicineDTO("Medicine1", BigDecimal.valueOf(25.5), "ExId1", "Pharmacy1"),
                new MedicineDTO("Medicine2", BigDecimal.valueOf(80), "ExId2", "Pharmacy2"),
                new MedicineDTO("Medicine3", BigDecimal.valueOf(5.5), "ExId3", "Pharmacy3")
        );

        List<MedicineDTO> list = csvParserService.parseIS(resource);

        assertEquals(dtos.size(), list.size());
        assertEquals(dtos.get(0).getTitle(), list.get(0).getTitle());

    }
}