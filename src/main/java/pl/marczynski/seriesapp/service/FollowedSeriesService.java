package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.FollowedSeries;

import java.util.List;
import java.util.Optional;

public interface FollowedSeriesService {

    FollowedSeries save(FollowedSeries followedSeries);

    List<FollowedSeries> findAll();

    Optional<FollowedSeries> findById(Long id);

    void deleteById(Long id);

    FollowedSeries update(FollowedSeries followedSeries);

    Float getAverageRate(Long seriesId);

    Integer getRateCount(Long seriesId);
}
