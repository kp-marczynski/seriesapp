package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.Season;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for Season.
 */
public interface SeasonService {

    /**
     * Save Season
     * @param Season Season to save
     * @return Season
     */
    Season save(Season Season);

    /**
     * Find all Season
     * @return List of Season
     */
    List<Season> findAll();

    /**
     * Find Season by id
     * @param id the id of the season to find
     * @return Optional of Season
     */
    Optional<Season> findById(Long id);

    /**
     * Delete Season by id
     * @param id the id of the season to delete
     */
    void deleteById(Long id);

    /**
     * Update an existing season
     *
     * @param season the season to update
     * @return Season
     */
    Season update(Season season);
}
