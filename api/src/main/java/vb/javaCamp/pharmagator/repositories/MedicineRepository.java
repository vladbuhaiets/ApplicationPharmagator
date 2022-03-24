package vb.javaCamp.pharmagator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vb.javaCamp.pharmagator.entities.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
