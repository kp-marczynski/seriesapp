package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.WatchedEpisode;

import java.util.List;
import java.util.Optional;

public interface WatchedEpisodeService {

    WatchedEpisode save(WatchedEpisode watchedEpisode);

    List<WatchedEpisode> findAll();

    Optional<WatchedEpisode> findById(Long id);

    void deleteById(Long id);

    WatchedEpisode update(WatchedEpisode watchedEpisode);

    Float getAverageRate(Long episodeId);
}
