package vb.javaCamp.pharmagator.services.impl;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.projections.MedicinePrice;
import vb.javaCamp.pharmagator.repositories.PharmacyRepository;
import vb.javaCamp.pharmagator.repositories.PriceRepository;
import vb.javaCamp.pharmagator.services.ExportServiceToCsv;

import javax.servlet.ServletOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportServiceToCsvImpl implements ExportServiceToCsv {

    private final PharmacyRepository pharmacyRepository;

    private final PriceRepository priceRepository;

    private static final String DELIMITER = ",";


    @SneakyThrows
    public void getExportData(ServletOutputStream outputStream) {

        String[] headers = createHeaders();
        CsvWriterSettings writerSettings = new CsvWriterSettings();
        writerSettings.setHeaders(headers);
        writerSettings.setAutoConfigurationEnabled(true);
        writerSettings.setNormalizeLineEndingsWithinQuotes(true);
        writerSettings.setHeaderWritingEnabled(true);

        CsvWriter csvWriter = new CsvWriter(outputStream,"UTF-8",writerSettings);

        writeDataToCsv(csvWriter);

        csvWriter.close();
    }

    private void writeDataToCsv(CsvWriter csvWriter) {

        List<MedicinePrice> list = priceRepository.findAllMedicinesPrices();
        list.stream()
                .map(this::createRow)
                .forEach(csvWriter::writeRow);
    }

    private String[] createRow(MedicinePrice medicinePrice) {

        StringBuilder stringBuilder = new StringBuilder();

        Pharmacy pharmacy = pharmacyRepository.findById(medicinePrice.getPharmacyId()).orElseThrow(IllegalArgumentException::new);

        stringBuilder.append(medicinePrice.getTitle());
        stringBuilder.append(DELIMITER);
        stringBuilder.append(medicinePrice.getPrice().toString());
        stringBuilder.append(DELIMITER);
        stringBuilder.append(pharmacy.getName());
        return new String[]{stringBuilder.toString()};

    }

    private String[] createHeaders() {

        return new String[]{"MedicineTitle" + DELIMITER, "Price" + DELIMITER, "PharmacyName"};

    }


}
