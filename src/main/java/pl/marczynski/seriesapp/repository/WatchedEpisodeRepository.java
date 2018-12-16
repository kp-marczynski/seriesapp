package pl.marczynski.seriesapp.repository;

import org.springframework.data.repository.query.Param;
import pl.marczynski.seriesapp.config.Constants;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the WatchedEpisode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchedEpisodeRepository extends JpaRepository<WatchedEpisode, Long> {

    /**
     * Find list of WatchedEpisode for current user
     * @return List of WatchedEpisode
     */
    @Query("select watched_episode from WatchedEpisode watched_episode where watched_episode.user.login = ?#{principal.username}")
    List<WatchedEpisode> findByUserIsCurrentUser();

    /**
     * Find if current user watched episode, which id is episodeId
     * @param episodeId the id of the Episode to check
     * @return Optional of WatchedEpisode
     */
    @Query("select watched_episode from WatchedEpisode watched_episode where watched_episode.user.login = ?#{principal.username} and watched_episode.episode.id = :episodeId")
    Optional<WatchedEpisode> findByEpisodeIdAndUserIsCurrentUser(@Param("episodeId") Long episodeId);

    /**
     * Get an average rate for episode, which id is episodeId
     * @param episodeId the id of the episode to check
     * @return Float
     */
    @Query("select avg(case watched_episode.rate " +
        "when 'BAD' then 1 " +
        "when 'MEDIOCRE' then 2 " +
        "when 'AVERAGE' then 3 " +
        "when 'GOOD' then 4 " +
        "when 'AWESOME' then 5 " +
        "else 0 END) from WatchedEpisode as watched_episode where watched_episode.episode.id = :episodeId and watched_episode.rate is not null")
    Float getAverageRateByEpisodeId(@Param("episodeId") Long episodeId);

    /**
     * Get a count of how many users watched episode, which id is episodeId
     * @param episodeId the id of the episode to check
     * @return Integer
     */
    @Query("select count(watched_episode) from WatchedEpisode as watched_episode where watched_episode.episode.id = :episodeId and watched_episode.rate is not null")
    Integer getRateCountByEpisodeId(@Param("episodeId") Long episodeId);
}
