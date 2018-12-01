package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Season;
import pl.marczynski.seriesapp.repository.SeasonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {
    private SeasonRepository SeasonRepository;

    public SeasonService(SeasonRepository SeasonRepository) {
        this.SeasonRepository = SeasonRepository;
    }

    public Season save(Season Season) {
        return SeasonRepository.save(Season);
    }

    public List<Season> findAll() {
        return SeasonRepository.findAll();
    }

    public Optional<Season> findById(Long id) {
        return SeasonRepository.findById(id);
    }

    public void deleteById(Long id) {
        SeasonRepository.deleteById(id);
    }
}
