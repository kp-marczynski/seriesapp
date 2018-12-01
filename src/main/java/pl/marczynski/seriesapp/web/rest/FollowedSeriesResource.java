package pl.marczynski.seriesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.service.FollowedSeriesService;
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
 * REST controller for managing FollowedSeries.
 */
@RestController
@RequestMapping("/api")
public class FollowedSeriesResource {

    private final Logger log = LoggerFactory.getLogger(FollowedSeriesResource.class);

    private static final String ENTITY_NAME = "followedSeries";

    private final FollowedSeriesService followedSeriesService;

    public FollowedSeriesResource(FollowedSeriesService followedSeriesService) {
        this.followedSeriesService = followedSeriesService;
    }

    /**
     * POST  /followed-series : Create a new followedSeries.
     *
     * @param followedSeries the followedSeries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new followedSeries, or with status 400 (Bad Request) if the followedSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/followed-series")
    @Timed
    public ResponseEntity<FollowedSeries> createFollowedSeries(@Valid @RequestBody FollowedSeries followedSeries) throws URISyntaxException {
        log.debug("REST request to save FollowedSeries : {}", followedSeries);
        if (followedSeries.getId() != null) {
            throw new BadRequestAlertException("A new followedSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FollowedSeries result = followedSeriesService.save(followedSeries);
        return ResponseEntity.created(new URI("/api/followed-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /followed-series : Updates an existing followedSeries.
     *
     * @param followedSeries the followedSeries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated followedSeries,
     * or with status 400 (Bad Request) if the followedSeries is not valid,
     * or with status 500 (Internal Server Error) if the followedSeries couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/followed-series")
    @Timed
    public ResponseEntity<FollowedSeries> updateFollowedSeries(@Valid @RequestBody FollowedSeries followedSeries) throws URISyntaxException {
        log.debug("REST request to update FollowedSeries : {}", followedSeries);
        if (followedSeries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FollowedSeries result = followedSeriesService.save(followedSeries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, followedSeries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /followed-series : get all the followedSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of followedSeries in body
     */
    @GetMapping("/followed-series")
    @Timed
    public List<FollowedSeries> getAllFollowedSeries() {
        log.debug("REST request to get all FollowedSeries");
        return followedSeriesService.findAll();
    }

    /**
     * GET  /followed-series/:id : get the "id" followedSeries.
     *
     * @param id the id of the followedSeries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the followedSeries, or with status 404 (Not Found)
     */
    @GetMapping("/followed-series/{id}")
    @Timed
    public ResponseEntity<FollowedSeries> getFollowedSeries(@PathVariable Long id) {
        log.debug("REST request to get FollowedSeries : {}", id);
        Optional<FollowedSeries> followedSeries = followedSeriesService.findById(id);
        return ResponseUtil.wrapOrNotFound(followedSeries);
    }

    /**
     * DELETE  /followed-series/:id : delete the "id" followedSeries.
     *
     * @param id the id of the followedSeries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/followed-series/{id}")
    @Timed
    public ResponseEntity<Void> deleteFollowedSeries(@PathVariable Long id) {
        log.debug("REST request to delete FollowedSeries : {}", id);

        followedSeriesService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
