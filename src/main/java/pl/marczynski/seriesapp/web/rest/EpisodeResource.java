package pl.marczynski.seriesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.security.AuthoritiesConstants;
import pl.marczynski.seriesapp.service.EpisodeService;
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
 * REST controller for managing Episode.
 */
@RestController
@RequestMapping("/api")
public class EpisodeResource {

    private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

    private static final String ENTITY_NAME = "episode";

    private final EpisodeService episodeService;

    public EpisodeResource(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    /**
     * POST  /episodes : Create a new episode.
     *
     * @param episode the episode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new episode, or with status 400 (Bad Request) if the episode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/episodes")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Episode> createEpisode(@Valid @RequestBody Episode episode) throws URISyntaxException {
        log.debug("REST request to save Episode : {}", episode);
        if (episode.getId() != null) {
            throw new BadRequestAlertException("A new episode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Episode result = episodeService.save(episode);
        return ResponseEntity.created(new URI("/api/episodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /episodes : Updates an existing episode.
     *
     * @param episode the episode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated episode,
     * or with status 400 (Bad Request) if the episode is not valid,
     * or with status 500 (Internal Server Error) if the episode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/episodes")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Episode> updateEpisode(@Valid @RequestBody Episode episode) throws URISyntaxException {
        log.debug("REST request to update Episode : {}", episode);
        if (episode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Episode result = episodeService.update(episode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, episode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /episodes : get all the episodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of episodes in body
     */
    @GetMapping("/episodes")
    @Timed
    public List<Episode> getAllEpisodes() {
        log.debug("REST request to get all Episodes");
        return episodeService.findAll();
    }

    /**
     * GET  /episodes/:id : get the "id" episode.
     *
     * @param id the id of the episode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the episode, or with status 404 (Not Found)
     */
    @GetMapping("/episodes/{id}")
    @Timed
    public ResponseEntity<Episode> getEpisode(@PathVariable Long id) {
        log.debug("REST request to get Episode : {}", id);
        Optional<Episode> episode = episodeService.findById(id);
        return ResponseUtil.wrapOrNotFound(episode);
    }

    /**
     * GET  /episodes/:id/watched : get the "id" watchedEpisode.
     *
     * @param id the id of the episode in the watchedEpisode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the watchedEpisode, or with status 404 (Not Found)
     */
    @GetMapping("/episodes/{id}/watched")
    @Timed
    public ResponseEntity<WatchedEpisode> getWatchedEpisodeForEpisodeId(@PathVariable Long id) {
        log.debug("REST request to get WatchedEpisode by Episode: {}", id);
        Optional<WatchedEpisode> watchedEpisode = episodeService.findWatchedByEpisodeId(id);
        return ResponseUtil.wrapOrNotFound(watchedEpisode);
    }

    /**
     * DELETE  /episodes/:id : delete the "id" episode.
     *
     * @param id the id of the episode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/episodes/{id}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        log.debug("REST request to delete Episode : {}", id);

        episodeService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
