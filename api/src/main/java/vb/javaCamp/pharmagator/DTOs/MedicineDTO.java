package vb.javaCamp.pharmagator.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {

    private String title;

    private BigDecimal price;

    private String externalId;

    private String pharmacyName;

}
