package pl.marczynski.seriesapp.repository;

import pl.marczynski.seriesapp.domain.Series;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Series entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByNameAndReleaseYear(@NotNull @Size(max = 150) String name, @NotNull @Min(value = 1926) Integer releaseYear);

    List<Series> findByNameContainingIgnoreCase(String search);
}
