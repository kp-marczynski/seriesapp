package pl.marczynski.seriesapp.repository;

import org.springframework.data.repository.query.Param;
import pl.marczynski.seriesapp.domain.Episode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Spring Data repository for the Episode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    /**
     * Find Episode by Series
     * @param year          the year of the series of episode to retrieve
     * @param name          the name of the series of episode to retrieve
     * @param seasonNumber  the number of the season of episode to retrieve
     * @param episodeNumber the number of episode to retrieve
     * @return Episode
     */
    @Query("select episode from Episode episode where episode.number = :episodeNumber and episode.season.number = :seasonNumber and episode.season.series.releaseYear = :year and episode.season.series.name = :name")
    Optional<Episode> findBySeries(@Param("year") Integer year, @Param("name") String name, @Param("seasonNumber") Integer seasonNumber, @Param("episodeNumber") Integer episodeNumber);
}
