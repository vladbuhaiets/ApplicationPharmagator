package vb.javaCamp.pharmagator.services;

import javax.servlet.ServletOutputStream;

public interface ExportServiceToCsv {

    void getExportData(ServletOutputStream outputStream);

}
