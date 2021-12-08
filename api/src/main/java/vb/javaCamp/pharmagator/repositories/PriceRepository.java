package vb.javaCamp.pharmagator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.entities.PriceId;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, PriceId> {

    List<Price> findAllByMedicineId(Long id);

}
