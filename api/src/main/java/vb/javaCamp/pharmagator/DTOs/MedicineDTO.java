package vb.javaCamp.pharmagator.DTOs;

import com.univocity.parsers.annotations.Parsed;
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

    @Parsed(field = "title")
    private String title;

    @Parsed(field = "price")
    private BigDecimal price;

    @Parsed(field = "externalId")
    private String externalId;

    @Parsed(field = "pharmacyName")
    private String pharmacyName;

}
