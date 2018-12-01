package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.repository.SeriesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    private SeriesRepository SeriesRepository;

    public SeriesService(SeriesRepository SeriesRepository) {
        this.SeriesRepository = SeriesRepository;
    }

    public Series save(Series Series) {
        return SeriesRepository.save(Series);
    }

    public List<Series> findAll() {
        return SeriesRepository.findAll();
    }

    public Optional<Series> findById(Long id) {
        return SeriesRepository.findById(id);
    }

    public void deleteById(Long id) {
        SeriesRepository.deleteById(id);
    }
}
