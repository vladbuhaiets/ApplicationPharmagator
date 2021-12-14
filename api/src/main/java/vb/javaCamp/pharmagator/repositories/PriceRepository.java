package vb.javaCamp.pharmagator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.entities.PriceId;
import vb.javaCamp.pharmagator.projections.MedicinePrice;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, PriceId> {

    List<Price> findAllByMedicineId(Long id);

    @Query("""
            SELECT p.price as price, m.title as title, p.pharmacyId as pharmacyId
            FROM Price p
            LEFT JOIN Medicine m ON m.id = p.medicineId
            """)
    List<MedicinePrice> findAllMedicinesPrices();

}
