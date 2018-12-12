package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.Series;

import java.util.List;
import java.util.Optional;

public interface SeriesService {

    Series save(Series Series);

    List<Series> findAll();

    Optional<Series> findById(Long id);

    void deleteById(Long id);

    Series update(Series series);

    Optional<FollowedSeries> findFollowedBySeriesId(Long id);

    Optional<Series> findByNameAndReleaseYear(String name, Integer releaseYear);
}
