package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.repository.SeriesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    private SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository SeriesRepository) {
        this.seriesRepository = SeriesRepository;
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
}
