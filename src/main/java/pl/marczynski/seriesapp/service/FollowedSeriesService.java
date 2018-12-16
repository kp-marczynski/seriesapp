package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.FollowedSeries;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for FollowedSeries.
 */
public interface FollowedSeriesService {

    /**
     * Save FollowedSeries
     * @param followedSeries followedSeries to save
     * @return FollowedSerie
     */
    FollowedSeries save(FollowedSeries followedSeries);

    /**
     * Find all FollowedSeries
     * @return List of FollowedSeries
     */
    List<FollowedSeries> findAll();

    /**
     * Find FollowedSeries by id
     * @param id the id of the FollowedSeries to find
     * @return Optional of FollowedSeries
     */
    Optional<FollowedSeries> findById(Long id);

    /**
     * Delete FollowedSeries by id
     * @param id the id of the FollowedSeries to delete
     */
    void deleteById(Long id);


    /**
     * Update an existing followedSeries
     *
     * @param followedSeries followedSeries to update
     * @return FollowedSeries
     */
    FollowedSeries update(FollowedSeries followedSeries);

    /**
     * Get an average rate for series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Float
     */
    Float getAverageRate(Long seriesId);

    /**
     * Get a count of how many users are following series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Integer
     */
    Integer getRateCount(Long seriesId);

    /**
     * Find if current user is following series, which id is id
     * @param id the id of the Series to check
     * @return Optional of FollowedSeries
     */
    Optional<FollowedSeries> findFollowedBySeriesId(Long id);
}
