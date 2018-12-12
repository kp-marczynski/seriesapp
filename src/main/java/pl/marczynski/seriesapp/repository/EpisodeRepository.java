package pl.marczynski.seriesapp.repository;

import org.springframework.data.repository.query.Param;
import pl.marczynski.seriesapp.domain.Episode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Episode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    @Query("select episode from Episode episode where episode.number = :episodeNumber and episode.season.number = :seasonNumber and episode.season.series.releaseYear = :year and episode.season.series.name = :name")
    Optional<Episode> findBySeries(@Param("year") Integer year, @Param("name") String name, @Param("seasonNumber") Integer seasonNumber, @Param("episodeNumber") Integer episodeNumber);
}
