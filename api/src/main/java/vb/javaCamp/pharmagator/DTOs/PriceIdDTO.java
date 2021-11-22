package vb.javaCamp.pharmagator.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceIdDTO {

    private Long pharmacyId;

    private Long medicineId;

}
