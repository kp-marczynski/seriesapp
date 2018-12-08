package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.repository.FollowedSeriesRepository;
import pl.marczynski.seriesapp.repository.UserRepository;
import pl.marczynski.seriesapp.security.AuthoritiesConstants;
import pl.marczynski.seriesapp.security.SecurityUtils;
import pl.marczynski.seriesapp.service.FollowedSeriesService;

import java.util.List;
import java.util.Optional;

@Service
public class FollowedSeriesServiceImpl implements FollowedSeriesService {
    private FollowedSeriesRepository followedSeriesRepository;
    private UserRepository userRepository;

    public FollowedSeriesServiceImpl(FollowedSeriesRepository FollowedSeriesRepository, UserRepository userRepository) {
        this.followedSeriesRepository = FollowedSeriesRepository;
        this.userRepository = userRepository;
    }

    public FollowedSeries save(FollowedSeries followedSeries) {
        FollowedSeries result = null;
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            Optional<User> user = userRepository.findOneByLogin(currentUserLogin.get());
            Optional<FollowedSeries> followedSeriesOptional = followedSeriesRepository.findByUserLoginAndSeriesId(currentUserLogin.get(), followedSeries.getSeries().getId());
            followedSeriesOptional.ifPresent(followedSeries1 -> followedSeries.setId(followedSeries1.getId()));
            if (user.isPresent()) {
                followedSeries.setUser(user.get());
                result = followedSeriesRepository.save(followedSeries);
            }
        }
        return result;
    }

    public List<FollowedSeries> findAll() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return followedSeriesRepository.findAll();
        } else {
            return followedSeriesRepository.findByUserIsCurrentUser();
        }
    }

    public Optional<FollowedSeries> findById(Long id) {
        return followedSeriesRepository.findById(id);
    }

    public void deleteById(Long id) {
        followedSeriesRepository.deleteById(id);
    }

    public FollowedSeries update(FollowedSeries followedSeries) {
        return save(followedSeries);
    }

    public Float getAverageRate(Long seriesId){
        return followedSeriesRepository.getAverageRateBySeriesId(seriesId);
    }
}
