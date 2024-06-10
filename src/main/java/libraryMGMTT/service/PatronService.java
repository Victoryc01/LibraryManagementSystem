package libraryMGMTT.service;

import libraryMGMTT.entity.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronService {
    List<Patron> getAllPatron();
    Patron getPatronById(Long id);
    Patron addPatron(Patron patron);
    Optional<Patron> updatePatron(Long id, Patron patronDetails);
    void deletePatron(Long id);
}
