package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.*;
import pl.marczynski.seriesapp.domain.builder.WatchedEpisodeBuilder;
import pl.marczynski.seriesapp.repository.EpisodeRepository;
import pl.marczynski.seriesapp.repository.UserRepository;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.security.SecurityUtils;
import pl.marczynski.seriesapp.service.EpisodeService;
import pl.marczynski.seriesapp.service.SeriesService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    private EpisodeRepository episodeRepository;
    private WatchedEpisodeRepository watchedEpisodeRepository;
    private UserRepository userRepository;
    private SeriesService seriesService;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository, WatchedEpisodeRepository watchedEpisodeRepository, UserRepository userRepository, SeriesService seriesService) {
        this.episodeRepository = episodeRepository;
        this.watchedEpisodeRepository = watchedEpisodeRepository;
        this.userRepository = userRepository;
        this.seriesService = seriesService;
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
            if (!result.isPresent()) {
                Optional<User> user = userRepository.findOneByLogin(currentUserLogin.get());
                Optional<Episode> episode = episodeRepository.findById(id);
                if (user.isPresent() && episode.isPresent()) {
                    result = Optional.of(new WatchedEpisodeBuilder().user(user.get()).episode(episode.get()).build());
                }
            }
        }
        return result;
    }

    @Override
    public Optional<Episode> findEpisodeFromSeries(Integer year, String name, Integer seasonNumber, Integer episodeNumber) {
        return episodeRepository.findBySeries(year, name, seasonNumber, episodeNumber);
    }

}
