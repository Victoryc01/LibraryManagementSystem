package libraryMGMTT.repository;

import libraryMGMTT.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepo extends JpaRepository<Patron, Long> {
}
