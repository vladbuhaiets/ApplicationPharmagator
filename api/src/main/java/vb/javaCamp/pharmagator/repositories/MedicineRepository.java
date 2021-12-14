package vb.javaCamp.pharmagator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vb.javaCamp.pharmagator.entities.Medicine;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Optional<Medicine> findByTitle(String title);

}
