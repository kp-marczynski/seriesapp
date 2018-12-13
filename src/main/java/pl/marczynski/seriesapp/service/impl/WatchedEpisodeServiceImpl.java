package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.*;
import pl.marczynski.seriesapp.domain.builder.FollowedSeriesBuilder;
import pl.marczynski.seriesapp.domain.builder.WatchedEpisodeBuilder;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.security.AuthoritiesConstants;
import pl.marczynski.seriesapp.security.SecurityUtils;
import pl.marczynski.seriesapp.service.EpisodeService;
import pl.marczynski.seriesapp.service.UserService;
import pl.marczynski.seriesapp.service.WatchedEpisodeService;

import java.util.List;
import java.util.Optional;

@Service
public class WatchedEpisodeServiceImpl implements WatchedEpisodeService {
    private WatchedEpisodeRepository watchedEpisodeRepository;
    private FollowedSeriesServiceImpl followedSeriesService;
    private UserService userService;
    private EpisodeService episodeService;

    public WatchedEpisodeServiceImpl(WatchedEpisodeRepository watchedEpisodeRepository, UserService userService, FollowedSeriesServiceImpl followedSeriesService, EpisodeService episodeService) {
        this.watchedEpisodeRepository = watchedEpisodeRepository;
        this.userService = userService;
        this.followedSeriesService = followedSeriesService;
        this.episodeService = episodeService;
    }

    @Override
    public WatchedEpisode save(WatchedEpisode watchedEpisode) {
        WatchedEpisode result = null;
        Optional<User> user = userService.findCurrentUser();
        Optional<WatchedEpisode> watchedEpisodeOptional = watchedEpisodeRepository.findByEpisodeIdAndUserIsCurrentUser(watchedEpisode.getEpisode().getId());
        watchedEpisodeOptional.ifPresent(watchedEpisode1 -> watchedEpisode.setId(watchedEpisode1.getId()));

        if (user.isPresent()) {

            watchedEpisode.setUser(user.get());
            result = watchedEpisodeRepository.save(watchedEpisode);
            Series series = watchedEpisode.getEpisode().getSeason().getSeries();
            FollowedSeries followedSeries = new FollowedSeriesBuilder().series(series).user(user.get()).build();
            followedSeriesService.save(followedSeries);
        }
        return result;
    }

    @Override
    public List<WatchedEpisode> findAll() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return watchedEpisodeRepository.findAll();
        } else {
            return watchedEpisodeRepository.findByUserIsCurrentUser();
        }
    }

    @Override
    public Optional<WatchedEpisode> findById(Long id) {
        return watchedEpisodeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        watchedEpisodeRepository.deleteById(id);
    }

    @Override
    public WatchedEpisode update(WatchedEpisode watchedEpisode) {
        return save(watchedEpisode);
    }

    @Override
    public Float getAverageRate(Long episodeId) {
        return watchedEpisodeRepository.getAverageRateByEpisodeId(episodeId);
    }

    @Override
    public Integer getRateCount(Long episodeId) {
        return watchedEpisodeRepository.getRateCountByEpisodeId(episodeId);
    }

    @Override
    public Optional<WatchedEpisode> findWatchedByEpisodeId(Long id) {
        Optional<WatchedEpisode> result;
        result = watchedEpisodeRepository.findByEpisodeIdAndUserIsCurrentUser(id);
        if (!result.isPresent()) {
            Optional<User> user = userService.findCurrentUser();
            Optional<Episode> episode = episodeService.findById(id);
            if (user.isPresent() && episode.isPresent()) {
                result = Optional.of(new WatchedEpisodeBuilder().user(user.get()).episode(episode.get()).build());
            }
        }
        return result;
    }
}
