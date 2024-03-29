package pl.marczynski.seriesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.service.WatchedEpisodeService;
import pl.marczynski.seriesapp.web.rest.errors.BadRequestAlertException;
import pl.marczynski.seriesapp.web.rest.jhipster.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WatchedEpisode.
 */
@RestController
@RequestMapping("/api")
public class WatchedEpisodeResource {

    private final Logger log = LoggerFactory.getLogger(WatchedEpisodeResource.class);

    private static final String ENTITY_NAME = "watchedEpisode";

    private final WatchedEpisodeService watchedEpisodeService;

    public WatchedEpisodeResource(WatchedEpisodeService watchedEpisodeService) {
        this.watchedEpisodeService = watchedEpisodeService;
    }

    /**
     * POST  /watched-episodes : Create a new watchedEpisode.
     *
     * @param watchedEpisode the watchedEpisode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new watchedEpisode, or with status 400 (Bad Request) if the watchedEpisode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/watched-episodes")
    @Timed
    public ResponseEntity<WatchedEpisode> createWatchedEpisode(@Valid @RequestBody WatchedEpisode watchedEpisode) throws URISyntaxException {
        log.debug("REST request to save WatchedEpisode : {}", watchedEpisode);
        if (watchedEpisode.getId() != null) {
            throw new BadRequestAlertException("A new watchedEpisode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WatchedEpisode result = watchedEpisodeService.save(watchedEpisode);
        return ResponseEntity.created(new URI("/api/watched-episodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /watched-episodes : Updates an existing watchedEpisode.
     *
     * @param watchedEpisode the watchedEpisode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated watchedEpisode,
     * or with status 400 (Bad Request) if the watchedEpisode is not valid,
     * or with status 500 (Internal Server Error) if the watchedEpisode couldn't be updated
     */
    @PutMapping("/watched-episodes")
    @Timed
    public ResponseEntity<WatchedEpisode> updateWatchedEpisode(@Valid @RequestBody WatchedEpisode watchedEpisode) {
        log.debug("REST request to update WatchedEpisode : {}", watchedEpisode);
        if (watchedEpisode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WatchedEpisode result = watchedEpisodeService.update(watchedEpisode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, watchedEpisode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /watched-episodes : get all the watchedEpisodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of watchedEpisodes in body
     */
    @GetMapping("/watched-episodes")
    @Timed
    public List<WatchedEpisode> getAllWatchedEpisodes() {
        log.debug("REST request to get all WatchedEpisodes");
        return watchedEpisodeService.findAll();
    }

    /**
     * GET  /watched-episodes/:id : get the watchedEpisode by "id".
     *
     * @param id the id of the watchedEpisode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the watchedEpisode, or with status 404 (Not Found)
     */
    @GetMapping("/watched-episodes/{id}")
    @Timed
    public ResponseEntity<WatchedEpisode> getWatchedEpisode(@PathVariable Long id) {
        log.debug("REST request to get WatchedEpisode : {}", id);
        Optional<WatchedEpisode> watchedEpisode = watchedEpisodeService.findById(id);
        return ResponseUtil.wrapOrNotFound(watchedEpisode);
    }

    /**
     * GET  /watched-episodes/episode/:id : get the watchedEpisode by "id".
     *
     * @param id the id of the episode in the watchedEpisode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the watchedEpisode, or with status 404 (Not Found)
     */
    @GetMapping("/watched-episodes/episode/{id}")
    @Timed
    public ResponseEntity<WatchedEpisode> getWatchedEpisodeForEpisodeId(@PathVariable Long id) {
        log.debug("REST request to get WatchedEpisode by Episode: {}", id);
        Optional<WatchedEpisode> watchedEpisode = watchedEpisodeService.getWatchedEpisodeForCurrentUserByEpisodeId(id);
        return ResponseUtil.wrapOrNotFound(watchedEpisode);
    }

    /**
     * GET  /watched-episodes/:id/average-rate : Get an average rate for episodes, which id is episodeId
     *
     * @param id the id of the watchedEpisode to check
     * @return the ResponseEntity with status 200 (OK) and with body the watchedEpisode, or with status 404 (Not Found)
     */
    @GetMapping("/watched-episodes/{id}/average-rate")
    @Timed
    public Float getAverageRate(@PathVariable Long id) {
        log.debug("REST request to get WatchedEpisode : {}", id);
        return watchedEpisodeService.getAverageRate(id);
    }

    /**
     * GET  /watched-episodes/:id/rate-count : Get a count of how many users watched episode, which id is episodeId
     *
     * @param id the id of the watchedEpisode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the watchedEpisode, or with status 404 (Not Found)
     */
    @GetMapping("/watched-episodes/{id}/rate-count")
    @Timed
    public Integer getRateCount(@PathVariable Long id) {
        log.debug("REST request to get WatchedEpisode : {}", id);
        return watchedEpisodeService.getRateCount(id);
    }


    /**
     * DELETE  /watched-episodes/:id : delete the watchedEpisode by "id".
     *
     * @param id the id of the watchedEpisode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/watched-episodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteWatchedEpisode(@PathVariable Long id) {
        log.debug("REST request to delete WatchedEpisode : {}", id);

        watchedEpisodeService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
