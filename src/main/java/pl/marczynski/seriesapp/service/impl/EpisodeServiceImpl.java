package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.repository.EpisodeRepository;
import pl.marczynski.seriesapp.repository.UserRepository;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.security.SecurityUtils;
import pl.marczynski.seriesapp.service.EpisodeService;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    private EpisodeRepository episodeRepository;
    private WatchedEpisodeRepository watchedEpisodeRepository;
    private UserRepository userRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository, WatchedEpisodeRepository watchedEpisodeRepository, UserRepository userRepository) {
        this.episodeRepository = episodeRepository;
        this.watchedEpisodeRepository = watchedEpisodeRepository;
        this.userRepository = userRepository;
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
            if(!result.isPresent()){
                Optional<User> user = userRepository.findOneByLogin(currentUserLogin.get());
                Optional<Episode> episode = episodeRepository.findById(id);
                if(user.isPresent() && episode.isPresent()){
                    result = Optional.of(new WatchedEpisode().user(user.get()).episode(episode.get()));
                }
            }
        }
        return result;
    }

}
