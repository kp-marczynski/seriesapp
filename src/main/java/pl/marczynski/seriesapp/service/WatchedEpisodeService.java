package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WatchedEpisodeService {
    private WatchedEpisodeRepository WatchedEpisodeRepository;

    public WatchedEpisodeService(WatchedEpisodeRepository WatchedEpisodeRepository) {
        this.WatchedEpisodeRepository = WatchedEpisodeRepository;
    }

    public WatchedEpisode save(WatchedEpisode WatchedEpisode) {
        return WatchedEpisodeRepository.save(WatchedEpisode);
    }

    public List<WatchedEpisode> findAll() {
        return WatchedEpisodeRepository.findAll();
    }

    public Optional<WatchedEpisode> findById(Long id) {
        return WatchedEpisodeRepository.findById(id);
    }

    public void deleteById(Long id) {
        WatchedEpisodeRepository.deleteById(id);
    }
}
