package vb.javaCamp.pharmagator.dataProviders;

import vb.javaCamp.pharmagator.DTOs.MedicineDTO;

import java.util.stream.Stream;

public interface DataProvider {

    Stream<MedicineDTO> loadData();

}
