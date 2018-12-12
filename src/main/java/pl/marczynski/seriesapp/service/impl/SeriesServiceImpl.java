package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.*;
import pl.marczynski.seriesapp.domain.builder.FollowedSeriesBuilder;
import pl.marczynski.seriesapp.repository.FollowedSeriesRepository;
import pl.marczynski.seriesapp.repository.SeriesRepository;
import pl.marczynski.seriesapp.repository.UserRepository;
import pl.marczynski.seriesapp.security.SecurityUtils;
import pl.marczynski.seriesapp.service.SeriesService;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesServiceImpl implements SeriesService {
    private SeriesRepository seriesRepository;
    private FollowedSeriesRepository followedSeriesRepository;
    private UserRepository userRepository;

    public SeriesServiceImpl(SeriesRepository SeriesRepository, FollowedSeriesRepository followedSeriesRepository, UserRepository userRepository) {
        this.seriesRepository = SeriesRepository;
        this.followedSeriesRepository = followedSeriesRepository;
        this.userRepository = userRepository;
    }

    public Series save(Series Series) {
        return seriesRepository.save(Series);
    }

    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    public Optional<Series> findById(Long id) {
        return seriesRepository.findById(id);
    }

    public void deleteById(Long id) {
        seriesRepository.deleteById(id);
    }

    public Series update(Series series) {
        return seriesRepository.save(series);
    }

    public Optional<FollowedSeries> findFollowedBySeriesId(Long id) {
        Optional<FollowedSeries> result = Optional.empty();
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            result = followedSeriesRepository.findByUserLoginAndSeriesId(currentUserLogin.get(), id);
            if (!result.isPresent()) {
                Optional<User> user = userRepository.findOneByLogin(currentUserLogin.get());
                Optional<Series> series = seriesRepository.findById(id);
                if (user.isPresent() && series.isPresent()) {
                    result = Optional.of(new FollowedSeriesBuilder().user(user.get()).series(series.get()).build());
                }
            }
        }
        return result;
    }

    @Override
    public Optional<Series> findByNameAndReleaseYear(String name, Integer releaseYear) {
        return seriesRepository.findByNameAndReleaseYear(name, releaseYear);
    }
}
