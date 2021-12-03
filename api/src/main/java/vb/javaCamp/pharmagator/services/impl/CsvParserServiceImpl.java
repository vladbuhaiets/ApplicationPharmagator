package vb.javaCamp.pharmagator.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.services.CsvParserService;

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

    private final String NAME = "name";
    private final String PRICE = "price";

    private final String DELIMITER = ",";

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
                .build();
    }

}
