package vb.javaCamp.pharmagator.projections;

import java.math.BigDecimal;

public interface MedicinePrice {

    String getTitle();

    long getPharmacyId();

    BigDecimal getPrice();
}
