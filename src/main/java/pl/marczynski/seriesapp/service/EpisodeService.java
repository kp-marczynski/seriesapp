package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.WatchedEpisode;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for episodes.
 */
public interface EpisodeService {

    /**
     * Save episode
     * @param episode episode to save
     * @return Episode
     */
    Episode save(Episode episode);

    /**
     * Find all episodes
     * @return List of Episode
     */
    List<Episode> findAll();

    /**
     * Find Episode by id
     * @param id the id of the episode to find
     * @return Optional of Episode
     */
    Optional<Episode> findById(Long id);

    /**
     * Delete Episode by id
     * @param id the id of the episode to delete
     */
    void deleteById(Long id);

    /**
     * Update an existing episode
     *
     * @param episode the episode to update
     * @return Episode
     */
    Episode update(Episode episode);

    /**
     * Get the episode from series and season.
     * @param year          the year of the series of episode to retrieve
     * @param name          the name of the series of episode to retrieve
     * @param seasonNumber  the number of the season of episode to retrieve
     * @param episodeNumber the number of episode to retrieve
     * @return Optional of Episode
     */
    Optional<Episode> findEpisodeFromSeries(Integer year, String name, Integer seasonNumber, Integer episodeNumber);
}
