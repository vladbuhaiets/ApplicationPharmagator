package vb.javaCamp.pharmagator.services;

import org.springframework.web.multipart.MultipartFile;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;

import java.io.InputStream;
import java.util.List;

public interface CsvParserService {

    String parseMultipartFile(MultipartFile file);

    List<MedicineDTO> parseIS(InputStream inputStream);

}
