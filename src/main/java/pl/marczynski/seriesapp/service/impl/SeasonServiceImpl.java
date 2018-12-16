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

    /**
     * Save Season
     * @param Season Season to save
     * @return Season
     */
    @Override
    public Season save(Season Season) {
        return seasonRepository.save(Season);
    }

    /**
     * Find all Season
     * @return List of Season
     */
    @Override
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    /**
     * Find Season by id
     * @param id the id of the season to find
     * @return Optional of Season
     */
    @Override
    public Optional<Season> findById(Long id) {
        return seasonRepository.findById(id);
    }

    /**
     * Delete Season by id
     * @param id the id of the season to delete
     */
    @Override
    public void deleteById(Long id) {
        seasonRepository.deleteById(id);
    }

    /**
     * Update an existing season
     *
     * @param season the season to update
     * @return Season
     */
    @Override
    public Season update(Season season) {
        return seasonRepository.save(season);
    }
}
