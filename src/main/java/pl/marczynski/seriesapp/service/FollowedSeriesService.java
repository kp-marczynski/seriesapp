package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.repository.FollowedSeriesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FollowedSeriesService {
    private FollowedSeriesRepository FollowedSeriesRepository;

    public FollowedSeriesService(FollowedSeriesRepository FollowedSeriesRepository) {
        this.FollowedSeriesRepository = FollowedSeriesRepository;
    }

    public FollowedSeries save(FollowedSeries FollowedSeries) {
        return FollowedSeriesRepository.save(FollowedSeries);
    }

    public List<FollowedSeries> findAll() {
        return FollowedSeriesRepository.findAll();
    }

    public Optional<FollowedSeries> findById(Long id) {
        return FollowedSeriesRepository.findById(id);
    }

    public void deleteById(Long id) {
        FollowedSeriesRepository.deleteById(id);
    }
}
