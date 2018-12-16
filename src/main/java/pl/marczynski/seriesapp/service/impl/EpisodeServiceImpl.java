package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.repository.EpisodeRepository;
import pl.marczynski.seriesapp.service.EpisodeService;

import java.util.List;
import java.util.Optional;
/**
 * Service class for managing episodes.
 */
@Service
public class EpisodeServiceImpl implements EpisodeService {
    private EpisodeRepository episodeRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    /**
     * Save episode
     * @param episode episode to save
     * @return Episode
     */
    @Override
    public Episode save(Episode episode) {
        return episodeRepository.save(episode);
    }

    /**
     * Find all episodes
     * @return List of Episode
     */
    @Override
    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    /**
     * Find Episode by id
     * @param id the id of the episode to find
     * @return Optional of Episode
     */
    @Override
    public Optional<Episode> findById(Long id) {
        return episodeRepository.findById(id);
    }

    /**
     * Delete Episode by id
     * @param id the id of the episode to delete
     */
    @Override
    public void deleteById(Long id) {
        episodeRepository.deleteById(id);
    }

    /**
     * Update an existing episode
     *
     * @param episode the episode to update
     * @return Episode
     */
    @Override
    public Episode update(Episode episode) {
        return episodeRepository.save(episode);
    }

    /**
     * Get the episode from series and season.
     * @param year          the year of the series of episode to retrieve
     * @param name          the name of the series of episode to retrieve
     * @param seasonNumber  the number of the season of episode to retrieve
     * @param episodeNumber the number of episode to retrieve
     * @return Optional of Episode
     */
    @Override
    public Optional<Episode> findEpisodeFromSeries(Integer year, String name, Integer seasonNumber, Integer episodeNumber) {
        return episodeRepository.findBySeries(year, name, seasonNumber, episodeNumber);
    }

}
