package vb.javaCamp.pharmagator.services;

import vb.javaCamp.pharmagator.DTOs.MedicineDTO;

import java.io.InputStream;
import java.util.List;

public interface CsvParserService {

    List<MedicineDTO> parse(InputStream inputStream);

}
