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

    @Override
    public Series save(Series Series) {
        return seriesRepository.save(Series);
    }

    @Override
    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    @Override
    public Optional<Series> findById(Long id) {
        return seriesRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        seriesRepository.deleteById(id);
    }

    @Override
    public Series update(Series series) {
        return seriesRepository.save(series);
    }

    @Override
    public Optional<Series> findByNameAndReleaseYear(String name, Integer releaseYear) {
        return seriesRepository.findByNameAndReleaseYear(name, releaseYear);
    }

    @Override
    public List<Series> findByNameContaining(String search) {
        return seriesRepository.findByNameContainingIgnoreCase(search);
    }
}
