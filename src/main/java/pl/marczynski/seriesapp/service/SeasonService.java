package pl.marczynski.seriesapp.service;

import pl.marczynski.seriesapp.domain.Season;

import java.util.List;
import java.util.Optional;

public interface SeasonService {

    Season save(Season Season);

    List<Season> findAll();

    Optional<Season> findById(Long id);

    void deleteById(Long id);

    Season update(Season season);
}
