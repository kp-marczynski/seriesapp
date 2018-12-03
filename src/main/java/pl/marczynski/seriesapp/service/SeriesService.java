package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.repository.FollowedSeriesRepository;
import pl.marczynski.seriesapp.repository.SeriesRepository;
import pl.marczynski.seriesapp.security.SecurityUtils;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    private SeriesRepository seriesRepository;
    private FollowedSeriesRepository followedSeriesRepository;

    public SeriesService(SeriesRepository SeriesRepository, FollowedSeriesRepository followedSeriesRepository) {
        this.seriesRepository = SeriesRepository;
        this.followedSeriesRepository = followedSeriesRepository;
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
        }
        return result;
    }
}
