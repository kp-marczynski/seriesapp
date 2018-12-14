package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.Series;

import java.util.List;
import java.util.Optional;

public interface SeriesService {

    /**
     * Save Series
     * @param Series Series to save
     * @return Series
     */
    Series save(Series Series);

    /**
     * Find all Series
     * @return List of Series
     */
    List<Series> findAll();

    /**
     * Find Series by id
     * @param id the id of the Series to find
     * @return Optional of Series
     */
    Optional<Series> findById(Long id);

    /**
     * Delete Series by id
     * @param id the id of the Series to delete
     */
    void deleteById(Long id);

    /**
     * Update an existing Series
     *
     * @param series the Series to update
     * @return Series
     */
    Series update(Series series);

    /**
     * Find Series by name and release year
     * @param name the name of the series to retrieve
     * @param releaseYear the year of the series to retrieve
     * @return Optional of Series
     */
    Optional<Series> findByNameAndReleaseYear(String name, Integer releaseYear);

    /**
     * Find Series by name (case doesn't matter)
     * @param search the name of the series to retrieve
     * @return List of Series
     */
    List<Series> findByNameContaining(String search);
}
