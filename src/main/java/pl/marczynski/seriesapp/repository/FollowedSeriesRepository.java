package pl.marczynski.seriesapp.repository;

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

    @Query("select followed_series from FollowedSeries followed_series where followed_series.user.login = ?#{principal.username}")
    List<FollowedSeries> findByUserIsCurrentUser();

    Optional<FollowedSeries> findByUserLoginAndSeriesId(@NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String user_login, Long series_id);
}
