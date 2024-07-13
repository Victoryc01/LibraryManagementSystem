package libraryMGMTT.service.serviceImpl;

import libraryMGMTT.entity.Patron;
import libraryMGMTT.repository.PatronRepo;
import libraryMGMTT.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepo patronRepo;

    @Override
    @Cacheable(value = "patrons")
    public List<Patron> getAllPatron() {
        return patronRepo.findAll();
    }

    @Override
    @Cacheable(value = "patron", key = "#id")
    public Patron getPatronById(Long id) {
        return patronRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No patron found with id " + id));
    }

    @Override
    @CachePut(value = "patron", key = "#patron.id")
    public Patron addPatron(Patron patron) {
        return patronRepo.save(patron);
    }

    @Override
    @CachePut(value = "patron", key = "#id")
    public Optional<Patron> updatePatron(Long id, Patron patronDetails) {
            return patronRepo.findById(id).map(patron -> {
                patron.setName(patronDetails.getName());
                patron.setContactInfo(patronDetails.getContactInfo());
                return patronRepo.save(patron);
            });
    }

    @Override
    @CacheEvict(value = "patron", key = "#id")
    public void deletePatron(Long id) {
        Patron deletePatron = patronRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No book found with id "+id));
        patronRepo.delete(deletePatron);
    }
}
