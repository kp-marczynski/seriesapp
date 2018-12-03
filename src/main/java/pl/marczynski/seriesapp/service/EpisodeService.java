package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.repository.EpisodeRepository;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.security.SecurityUtils;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {
    private EpisodeRepository episodeRepository;
    private WatchedEpisodeRepository watchedEpisodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository, WatchedEpisodeRepository watchedEpisodeRepository) {
        this.episodeRepository = episodeRepository;
        this.watchedEpisodeRepository = watchedEpisodeRepository;
    }

    public Episode save(Episode episode) {
        return episodeRepository.save(episode);
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public Optional<Episode> findById(Long id) {
        return episodeRepository.findById(id);
    }

    public void deleteById(Long id) {
        episodeRepository.deleteById(id);
    }

    public Episode update(Episode episode) {
        return episodeRepository.save(episode);
    }

    public Optional<WatchedEpisode> findWatchedByEpisodeId(Long id) {
        Optional<WatchedEpisode> result = Optional.empty();
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            result = watchedEpisodeRepository.findByUserLoginAndEpisodeId(currentUserLogin.get(), id);
        }
        return result;
    }

}
