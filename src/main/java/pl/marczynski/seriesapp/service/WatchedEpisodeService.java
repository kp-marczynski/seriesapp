package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.repository.UserRepository;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.security.AuthoritiesConstants;
import pl.marczynski.seriesapp.security.SecurityUtils;

import java.util.List;
import java.util.Optional;

@Service
public class WatchedEpisodeService {
    private WatchedEpisodeRepository watchedEpisodeRepository;
    private FollowedSeriesService followedSeriesService;
    private UserRepository userRepository;

    public WatchedEpisodeService(WatchedEpisodeRepository watchedEpisodeRepository, UserRepository userRepository, FollowedSeriesService followedSeriesService) {
        this.watchedEpisodeRepository = watchedEpisodeRepository;
        this.userRepository = userRepository;
        this.followedSeriesService = followedSeriesService;
    }

    public WatchedEpisode save(WatchedEpisode watchedEpisode) {
        WatchedEpisode result = null;
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            Optional<User> user = userRepository.findOneByLogin(currentUserLogin.get());
            Optional<WatchedEpisode> watchedEpisodeOptional = watchedEpisodeRepository.findByUserLoginAndEpisodeId(currentUserLogin.get(), watchedEpisode.getEpisode().getId());
            watchedEpisodeOptional.ifPresent(watchedEpisode1 -> watchedEpisode.setId(watchedEpisode1.getId()));

            if (user.isPresent()) {

                watchedEpisode.setUser(user.get());
                result = watchedEpisodeRepository.save(watchedEpisode);
                Series series = watchedEpisode.getEpisode().getSeason().getSeries();
                FollowedSeries followedSeries = new FollowedSeries().series(series).user(user.get());
                followedSeriesService.save(followedSeries);
            }
        }
        return result;
    }

    public List<WatchedEpisode> findAll() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return watchedEpisodeRepository.findAll();
        } else {
            return watchedEpisodeRepository.findByUserIsCurrentUser();
        }
    }

    public Optional<WatchedEpisode> findById(Long id) {
        return watchedEpisodeRepository.findById(id);
    }

    public void deleteById(Long id) {
        watchedEpisodeRepository.deleteById(id);
    }

    public WatchedEpisode update(WatchedEpisode watchedEpisode) {
        return save(watchedEpisode);
    }
}
