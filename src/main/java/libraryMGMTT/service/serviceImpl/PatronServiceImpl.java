package libraryMGMTT.service.serviceImpl;

import libraryMGMTT.entity.Patron;
import libraryMGMTT.repository.PatronRepo;
import libraryMGMTT.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepo patronRepo;

    @Override
    public List<Patron> getAllPatron() {
        return patronRepo.findAll();
    }

    @Override
    public Patron getPatronById(Long id) {
        return patronRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No patron found with id " + id));
    }

    @Override
    public Patron addPatron(Patron patron) {
        return patronRepo.save(patron);
    }

    @Override
    public Optional<Patron> updatePatron(Long id, Patron patronDetails) {
        if (patronRepo.existsById(id)) {
            return patronRepo.findById(id).map(patron -> {
                patron.setName(patronDetails.getName());
                patron.setContactInfo(patronDetails.getContactInfo());
                return patronRepo.save(patron);
            });
        }
        throw new RuntimeException("No patron found with id " + id);
    }

    @Override
    public void deletePatron(Long id) {
        Patron deletePatron = getPatronById(id);
        patronRepo.delete(deletePatron);
    }
}
