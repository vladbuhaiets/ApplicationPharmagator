package vb.javaCamp.pharmagator.mappers;

import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.entities.Medicine;
import vb.javaCamp.pharmagator.entities.Price;

public class MedicineMapper {

    private MedicineMapper() {
    }

    public static MedicineDTO entityToDto(Medicine medicine) {
        MedicineDTO dto = new MedicineDTO();
        dto.setTitle(medicine.getTitle());
        dto.setId(medicine.getId());
        return dto;
    }

    public static Medicine DtoToMedicineEntity(MedicineDTO dto) {
        Medicine medicine = new Medicine();
        medicine.setTitle(dto.getTitle());
        return medicine;
    }

    public static Price DtoToPriceEntity(MedicineDTO dto) {
        Price price = new Price();
        price.setMedicinePrice(dto.getPrice());
        price.setExternalId(dto.getExternalId());
        return price;
    }

}
