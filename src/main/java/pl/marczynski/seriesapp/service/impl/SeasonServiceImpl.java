package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Season;
import pl.marczynski.seriesapp.repository.SeasonRepository;
import pl.marczynski.seriesapp.service.SeasonService;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonServiceImpl implements SeasonService {
    private SeasonRepository seasonRepository;

    public SeasonServiceImpl(SeasonRepository SeasonRepository) {
        this.seasonRepository = SeasonRepository;
    }

    @Override
    public Season save(Season Season) {
        return seasonRepository.save(Season);
    }

    @Override
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    @Override
    public Optional<Season> findById(Long id) {
        return seasonRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        seasonRepository.deleteById(id);
    }

    @Override
    public Season update(Season season) {
        return seasonRepository.save(season);
    }
}
