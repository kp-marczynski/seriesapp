package pl.marczynski.seriesapp.service;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.repository.EpisodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {
    private EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public Episode save(Episode episode) {
        return episodeRepository.save(episode);
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public Optional<Episode> findById(Long id) {
        return episodeRepository.findById(id);
    }

    public void deleteById(Long id) {
        episodeRepository.deleteById(id);
    }
}
