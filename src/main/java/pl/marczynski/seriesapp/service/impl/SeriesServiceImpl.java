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

    public SeriesServiceImpl(SeriesRepository SeriesRepository) {
        this.seriesRepository = SeriesRepository;
    }

    /**
     * Save Series
     * @param Series Series to save
     * @return Series
     */
    @Override
    public Series save(Series Series) {
        return seriesRepository.save(Series);
    }

    /**
     * Find all Series
     * @return List of Series
     */
    @Override
    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    /**
     * Find Series by id
     * @param id the id of the Series to find
     * @return Optional of Series
     */
    @Override
    public Optional<Series> findById(Long id) {
        return seriesRepository.findById(id);
    }

    /**
     * Delete Series by id
     * @param id the id of the Series to delete
     */
    @Override
    public void deleteById(Long id) {
        seriesRepository.deleteById(id);
    }

    /**
     * Update an existing Series
     *
     * @param series the Series to update
     * @return Series
     */
    @Override
    public Series update(Series series) {
        return seriesRepository.save(series);
    }

    /**
     * Find Series by name and release year
     * @param name the name of the series to retrieve
     * @param releaseYear the year of the series to retrieve
     * @return Optional of Series
     */
    @Override
    public Optional<Series> findByNameAndReleaseYear(String name, Integer releaseYear) {
        return seriesRepository.findByNameAndReleaseYear(name, releaseYear);
    }

    /**
     * Find Series by name (case doesn't matter)
     * @param search the name of the series to retrieve
     * @return List of Series
     */
    @Override
    public List<Series> findByNameContaining(String search) {
        return seriesRepository.findByNameContainingIgnoreCase(search);
    }
}
