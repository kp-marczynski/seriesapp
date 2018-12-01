package pl.marczynski.seriesapp.repository;

import pl.marczynski.seriesapp.domain.WatchedEpisode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the WatchedEpisode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchedEpisodeRepository extends JpaRepository<WatchedEpisode, Long> {

    @Query("select watched_episode from WatchedEpisode watched_episode where watched_episode.user.login = ?#{principal.username}")
    List<WatchedEpisode> findByUserIsCurrentUser();

}
