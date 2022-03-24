package vb.javaCamp.pharmagator.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vb.javaCamp.pharmagator.services.ExportServiceToCsv;
import vb.javaCamp.pharmagator.services.ExportServiceToExcel;
import vb.javaCamp.pharmagator.services.ExportServiceToPdf;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportServiceToExcel exportServiceToExcel;

    private final ExportServiceToPdf exportServiceToPdf;

    private final ExportServiceToCsv exportServiceToCsv;

    private final String content = "Content-Disposition";

    @SneakyThrows
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) {

        XSSFWorkbook workbook = exportServiceToExcel.getExportData();
        ServletOutputStream outputStream = response.getOutputStream();

        response.addHeader(content, "attachment; filename=export.xlsx");

        workbook.write(outputStream);
        workbook.close();

    }

    @SneakyThrows
    @GetMapping("/pdf")
    public void exportToPdf(HttpServletResponse response) {

        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        response.addHeader(content, "attachment; filename=export.pdf");

        exportServiceToPdf.getExportData(response.getOutputStream());

    }

    @SneakyThrows
    @GetMapping("/csv")
    public void exportToCsv(HttpServletResponse response) {

        response.setContentType("text/csv");
        response.addHeader(content, "attachment; filename=export.csv");

        exportServiceToCsv.getExportData(response.getOutputStream());

    }



}
