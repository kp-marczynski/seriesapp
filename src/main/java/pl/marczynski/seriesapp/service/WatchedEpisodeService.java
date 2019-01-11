package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.WatchedEpisode;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for WatchedEpisode.
 */
public interface WatchedEpisodeService {

    /**
     * Save WatchedEpisode
     * @param watchedEpisode WatchedEpisode to save
     * @return WatchedEpisode
     */
    WatchedEpisode save(WatchedEpisode watchedEpisode);

    /**
     * Find all watchedEpisode
     * @return List of watchedEpisode
     */
    List<WatchedEpisode> findAll();

    /**
     * Find WatchedEpisode by id
     * @param id the id of the WatchedEpisode to find
     * @return Optional of WatchedEpisode
     */
    Optional<WatchedEpisode> findById(Long id);

    /**
     * Delete WatchedEpisode by id
     * @param id the id of the WatchedEpisode to delete
     */
    void deleteById(Long id);

    /**
     * Update an existing WatchedEpisode
     *
     * @param watchedEpisode WatchedEpisode to update
     * @return WatchedEpisode
     */
    WatchedEpisode update(WatchedEpisode watchedEpisode);

    /**
     * Get an average rate for episodes, which id is episodeId
     * @param episodeId the id of the episode to check
     * @return Float
     */
    Float getAverageRate(Long episodeId);

    /**
     * Get a count of how many users watched episode, which id is episodeId
     * @param episodeId the id of the episode to check
     * @return Integer
     */
    Integer getRateCount(Long episodeId);

    /**
     * Find if current user watched episode, which id is id
     * @param id the id of the episode to check
     * @return Optional of WatchedEpisode
     */
    Optional<WatchedEpisode> getWatchedEpisodeForCurrentUserByEpisodeId(Long id);
}
