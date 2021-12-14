package vb.javaCamp.pharmagator.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.entities.Pharmacy;
import vb.javaCamp.pharmagator.projections.MedicinePrice;
import vb.javaCamp.pharmagator.repositories.PharmacyRepository;
import vb.javaCamp.pharmagator.repositories.PriceRepository;
import vb.javaCamp.pharmagator.services.ExportServiceToPdf;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ExportServiceToPdfImpl implements ExportServiceToPdf {

    private final PriceRepository priceRepository;
    private final PharmacyRepository pharmacyRepository;

    private Map<String, Map<Long, BigDecimal>> pricesMap;

    private List<Pharmacy> pharmacies;

    private final String FONT = "api/src/main/resources/fonts/FreeSerif.ttf";

    @SneakyThrows
    public void getExportData(ServletOutputStream outputStream) {

        pricesMap = getMapPricesFromDatabase();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(24);
        font.setColor(BaseColor.ORANGE);

        Paragraph p = new Paragraph("List of Prices", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(p);

        pharmacies = pharmacyRepository.findAll();

        PdfPTable table = new PdfPTable(pharmacies.size() + 1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5);


        writeTableHeaders(table, pharmacies);

        writeDateToTable(table);

        document.add(table);

        document.close();
    }

    private void writeDateToTable(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setPadding(5);
        cell.setBorderWidth(2);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);

        try {

            BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12, Font.NORMAL);
            font.setColor(BaseColor.BLACK);

            pharmacies = pharmacyRepository.findAll();

            pricesMap.forEach((medicineTitle, phs) -> {

                cell.setBackgroundColor(BaseColor.YELLOW);
                cell.setPhrase(new Paragraph(medicineTitle, font));
                table.addCell(cell);

                Map<Long, BigDecimal> prices = pricesMap.get(medicineTitle);
                pharmacies.stream()
                        .map(Pharmacy::getId)
                        .map(pharmacy ->
                                Optional.ofNullable(prices.get(pharmacy))
                                        .map(String::valueOf)
                                        .orElseGet(String::new))
                        .map(price -> {
                            cell.setPhrase(Phrase.getInstance(price));
                            cell.setBackgroundColor(BaseColor.WHITE);
                            return cell;
                        })
                        .forEach(table::addCell);
            });

        } catch (DocumentException | IOException e) {
            log.warn(e);
        }

    }

    public Map<String, Map<Long, BigDecimal>> getMapPricesFromDatabase() {
        return priceRepository.findAllMedicinesPrices()
                .stream()
                .collect(Collectors.groupingBy(MedicinePrice::getTitle,
                        Collectors.toMap(MedicinePrice::getPharmacyId, MedicinePrice::getPrice)));
    }

    private void writeTableHeaders(PdfPTable table, List<Pharmacy> pharmacies) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        cell.setBorderWidth(2);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);


        Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font.setColor(BaseColor.BLACK);

        cell.setPhrase(Phrase.getInstance("medicineTitle"));
        table.addCell(cell);

        pharmacies.forEach(pharmacy -> {
            cell.setPhrase(Phrase.getInstance(pharmacy.getName()));
            table.addCell(cell);
        });

    }

}
