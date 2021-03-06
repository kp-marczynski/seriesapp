package pl.marczynski.seriesapp.repository;

import org.springframework.data.repository.query.Param;
import pl.marczynski.seriesapp.config.Constants;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FollowedSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowedSeriesRepository extends JpaRepository<FollowedSeries, Long> {

    /**
     * Find list of FollowedSeries for current user
     * @return List of FollowedSeries
     */
    @Query("select followed_series from FollowedSeries followed_series where followed_series.user.login = ?#{principal.username}")
    List<FollowedSeries> findByUserIsCurrentUser();

    /**
     * Find if current user is following series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Optional of FollowedSeries
     */
    @Query("select followed_series from FollowedSeries followed_series where followed_series.user.login = ?#{principal.username} and followed_series.series.id = :seriesId")
    Optional<FollowedSeries> findBySeriesIdAndUserIsCurrentUser(@Param("seriesId") Long seriesId);

    /**
     * Get an average rate for series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Float
     */
    @Query("select avg(case followed_series.rate " +
        "when 'BAD' then 1 " +
        "when 'MEDIOCRE' then 2 " +
        "when 'AVERAGE' then 3 " +
        "when 'GOOD' then 4 " +
        "when 'AWESOME' then 5 " +
        "else 0 END) from FollowedSeries as followed_series where followed_series.series.id = :seriesId and followed_series.rate is not null")
    Float getAverageRateBySeriesId(@Param("seriesId") Long seriesId);

    /**
     * Get a count of how many users are following series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Integer
     */
    @Query("select count(followed_series) from FollowedSeries as followed_series where followed_series.series.id = :seriesId and followed_series.rate is not null")
    Integer getRateCountBySeriesId(@Param("seriesId") Long seriesId);
}
