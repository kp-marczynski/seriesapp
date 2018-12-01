package pl.marczynski.seriesapp.repository;

import pl.marczynski.seriesapp.domain.FollowedSeries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FollowedSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowedSeriesRepository extends JpaRepository<FollowedSeries, Long> {

    @Query("select followed_series from FollowedSeries followed_series where followed_series.user.login = ?#{principal.username}")
    List<FollowedSeries> findByUserIsCurrentUser();

}
