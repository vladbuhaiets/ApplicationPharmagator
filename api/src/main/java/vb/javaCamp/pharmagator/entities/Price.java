package vb.javaCamp.pharmagator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
@IdClass(PriceId.class)
public class Price {

    @Id
    private Long pharmacyId;

    @Id
    private Long medicineId;

    @Min(value = 0)
    private BigDecimal price;

    private String externalId;

    @Column(insertable = false, updatable = false)
    @LastModifiedDate
    private Instant updatedAt;
}
