package vb.javaCamp.pharmagator.services.impl;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.services.CsvParserService;
import vb.javaCamp.pharmagator.services.SavingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvParserServiceImpl implements CsvParserService {

    private final CsvParser csvParser;
    private final BeanListProcessor<MedicineDTO> rowProcessor;
    private final SavingService savingService;

    private final String DELIMITER = ",";
    public static String TYPE = "text/csv";

    @Override
    public String parse(MultipartFile file) {

        if (file.isEmpty())
            return "File is empty!";

        if (TYPE.equals(file.getContentType())) {
            try {
                InputStream inputStream = file.getInputStream();
                csvParser.parse(inputStream);
                rowProcessor.getBeans().forEach(this::save);
                return "File : " + file.getOriginalFilename() + " is saved!";

            } catch (IOException e) {
                return "File is not saved... Something goes wrong";
            }
        }
        return "File has not correct type.";

    }

    private final String NAME = "name";
    private final String PRICE = "price";
    private final String EXTERNAL = "external";
    private final String PHARMACY = "pharmacy";

    @Override
    public List<MedicineDTO> parse(InputStream inputStream) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String header = reader.readLine();
            Map<String, Integer> headersMap = this.parseHeader(header);

            String line = reader.readLine();

            List<MedicineDTO> medicines = new ArrayList<>();

            while (Objects.nonNull(line)) {
                MedicineDTO medicineDTO = this.parse(line.split(DELIMITER), headersMap);
                medicines.add(medicineDTO);
                line = reader.readLine();
            }
            return medicines;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return List.of();
    }

    private Map<String, Integer> parseHeader(String header) {
        Map<String, Integer> headersMap = new HashMap<>();
        String[] headers = header.split(DELIMITER);

        for (int i = 0; i < headers.length; i++) {
            headersMap.put(headers[i], i);
        }

        return headersMap;

    }

    private MedicineDTO parse(String[] lines, Map<String, Integer> headers) {
        return MedicineDTO.builder()
                .title(lines[headers.get(NAME)])
                .price(new BigDecimal(lines[headers.get(PRICE)]))
                .externalId("from CSV file")
                .pharmacyName(lines[headers.get(PHARMACY)])
                .build();
    }

    private void save(MedicineDTO dto) {
        savingService.saveToDB(dto);
    }

}
