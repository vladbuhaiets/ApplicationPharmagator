package vb.javaCamp.pharmagator.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

    private Long pharmacyId;

    private Long medicineId;

    private BigDecimal price;

    private String externalId;

    private Instant updatedAt;

}
