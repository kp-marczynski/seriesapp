package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.WatchedEpisode;

import java.util.List;
import java.util.Optional;

public interface EpisodeService {

    Episode save(Episode episode);

    List<Episode> findAll();

    Optional<Episode> findById(Long id);

    void deleteById(Long id);

    Episode update(Episode episode);

    Optional<WatchedEpisode> findWatchedByEpisodeId(Long id);

    Optional<Episode> findEpisodeFromSeries(Integer year, String name, Integer seasonNumber, Integer episodeNumber);
}
