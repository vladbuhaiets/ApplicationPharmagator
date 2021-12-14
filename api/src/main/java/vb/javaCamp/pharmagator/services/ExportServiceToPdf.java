package vb.javaCamp.pharmagator.services;

import javax.servlet.ServletOutputStream;

public interface ExportServiceToPdf {

    void getExportData(ServletOutputStream outputStream);

}
