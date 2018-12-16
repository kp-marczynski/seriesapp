package pl.marczynski.seriesapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.marczynski.seriesapp.domain.Season;
import pl.marczynski.seriesapp.security.AuthoritiesConstants;
import pl.marczynski.seriesapp.service.SeasonService;
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
 * REST controller for managing Season.
 */
@RestController
@RequestMapping("/api")
public class SeasonResource {

    private final Logger log = LoggerFactory.getLogger(SeasonResource.class);

    private static final String ENTITY_NAME = "season";

    private final SeasonService seasonService;

    public SeasonResource(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    /**
     * POST  /seasons : Create a new season.
     *
     * @param season the season to create
     * @return the ResponseEntity with status 201 (Created) and with body the new season, or with status 400 (Bad Request) if the season has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Season> createSeason(@Valid @RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to save Season : {}", season);
        if (season.getId() != null) {
            throw new BadRequestAlertException("A new season cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Season result = seasonService.save(season);
        return ResponseEntity.created(new URI("/api/seasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons : Updates an existing season.
     *
     * @param season the season to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated season,
     * or with status 400 (Bad Request) if the season is not valid,
     * or with status 500 (Internal Server Error) if the season couldn't be updated
     */
    @PutMapping("/seasons")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Season> updateSeason(@Valid @RequestBody Season season) {
        log.debug("REST request to update Season : {}", season);
        if (season.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Season result = seasonService.update(season);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, season.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons : get all the seasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seasons in body
     */
    @GetMapping("/seasons")
    @Timed
    public List<Season> getAllSeasons() {
        log.debug("REST request to get all Seasons");
        return seasonService.findAll();
    }

    /**
     * GET  /seasons/:id : get the season by "id".
     *
     * @param id the id of the season to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the season, or with status 404 (Not Found)
     */
    @GetMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<Season> getSeason(@PathVariable Long id) {
        log.debug("REST request to get Season : {}", id);
        Optional<Season> season = seasonService.findById(id);
        return ResponseUtil.wrapOrNotFound(season);
    }

    /**
     * DELETE  /seasons/:id : delete the season by "id".
     *
     * @param id the id of the season to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons/{id}")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        log.debug("REST request to delete Season : {}", id);

        seasonService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
