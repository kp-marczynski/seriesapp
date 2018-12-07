package pl.marczynski.seriesapp.web.rest;

import pl.marczynski.seriesapp.SeriesappApp;

import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.builder.WatchedEpisodeBuilder;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.service.WatchedEpisodeService;
import pl.marczynski.seriesapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static pl.marczynski.seriesapp.web.rest.jhipster.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.marczynski.seriesapp.domain.enumeration.Rate;
import pl.marczynski.seriesapp.web.rest.jhipster.TestUtil;

/**
 * Test class for the WatchedEpisodeResource REST controller.
 *
 * @see WatchedEpisodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeriesappApp.class)
public class WatchedEpisodeResourceIntTest {

    private static final Rate DEFAULT_RATE = Rate.BAD;
    private static final Rate UPDATED_RATE = Rate.MEDIOCRE;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private WatchedEpisodeRepository watchedEpisodeRepository;

    @Autowired
    private WatchedEpisodeService watchedEpisodeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWatchedEpisodeMockMvc;

    private WatchedEpisode watchedEpisode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WatchedEpisodeResource watchedEpisodeResource = new WatchedEpisodeResource(watchedEpisodeService);
        this.restWatchedEpisodeMockMvc = MockMvcBuilders.standaloneSetup(watchedEpisodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchedEpisode createEntity(EntityManager em) {
        WatchedEpisode watchedEpisode = new WatchedEpisodeBuilder()
            .rate(DEFAULT_RATE)
            .comment(DEFAULT_COMMENT).build();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        watchedEpisode.setUser(user);
        // Add required entity
        Episode episode = EpisodeResourceIntTest.createEntity(em);
        em.persist(episode);
        em.flush();
        watchedEpisode.setEpisode(episode);
        return watchedEpisode;
    }

    @Before
    public void initTest() {
        watchedEpisode = createEntity(em);
    }

    @Test
    @Transactional
    public void createWatchedEpisode() throws Exception {
        int databaseSizeBeforeCreate = watchedEpisodeRepository.findAll().size();

        // Create the WatchedEpisode
        restWatchedEpisodeMockMvc.perform(post("/api/watched-episodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchedEpisode)))
            .andExpect(status().isCreated());

        // Validate the WatchedEpisode in the database
        List<WatchedEpisode> watchedEpisodeList = watchedEpisodeRepository.findAll();
        assertThat(watchedEpisodeList).hasSize(databaseSizeBeforeCreate + 1);
        WatchedEpisode testWatchedEpisode = watchedEpisodeList.get(watchedEpisodeList.size() - 1);
        assertThat(testWatchedEpisode.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testWatchedEpisode.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createWatchedEpisodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = watchedEpisodeRepository.findAll().size();

        // Create the WatchedEpisode with an existing ID
        watchedEpisode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchedEpisodeMockMvc.perform(post("/api/watched-episodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchedEpisode)))
            .andExpect(status().isBadRequest());

        // Validate the WatchedEpisode in the database
        List<WatchedEpisode> watchedEpisodeList = watchedEpisodeRepository.findAll();
        assertThat(watchedEpisodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWatchedEpisodes() throws Exception {
        // Initialize the database
        watchedEpisodeRepository.saveAndFlush(watchedEpisode);

        // Get all the watchedEpisodeList
        restWatchedEpisodeMockMvc.perform(get("/api/watched-episodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchedEpisode.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getWatchedEpisode() throws Exception {
        // Initialize the database
        watchedEpisodeRepository.saveAndFlush(watchedEpisode);

        // Get the watchedEpisode
        restWatchedEpisodeMockMvc.perform(get("/api/watched-episodes/{id}", watchedEpisode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(watchedEpisode.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWatchedEpisode() throws Exception {
        // Get the watchedEpisode
        restWatchedEpisodeMockMvc.perform(get("/api/watched-episodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWatchedEpisode() throws Exception {
        // Initialize the database
        watchedEpisodeRepository.saveAndFlush(watchedEpisode);

        int databaseSizeBeforeUpdate = watchedEpisodeRepository.findAll().size();

        // Update the watchedEpisode
        WatchedEpisode updatedWatchedEpisode = watchedEpisodeRepository.findById(watchedEpisode.getId()).get();
        // Disconnect from session so that the updates on updatedWatchedEpisode are not directly saved in db
        em.detach(updatedWatchedEpisode);
        updatedWatchedEpisode.setRate(UPDATED_RATE);
        updatedWatchedEpisode.setComment(UPDATED_COMMENT);

        restWatchedEpisodeMockMvc.perform(put("/api/watched-episodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWatchedEpisode)))
            .andExpect(status().isOk());

        // Validate the WatchedEpisode in the database
        List<WatchedEpisode> watchedEpisodeList = watchedEpisodeRepository.findAll();
        assertThat(watchedEpisodeList).hasSize(databaseSizeBeforeUpdate);
        WatchedEpisode testWatchedEpisode = watchedEpisodeList.get(watchedEpisodeList.size() - 1);
        assertThat(testWatchedEpisode.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testWatchedEpisode.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingWatchedEpisode() throws Exception {
        int databaseSizeBeforeUpdate = watchedEpisodeRepository.findAll().size();

        // Create the WatchedEpisode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchedEpisodeMockMvc.perform(put("/api/watched-episodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchedEpisode)))
            .andExpect(status().isBadRequest());

        // Validate the WatchedEpisode in the database
        List<WatchedEpisode> watchedEpisodeList = watchedEpisodeRepository.findAll();
        assertThat(watchedEpisodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWatchedEpisode() throws Exception {
        // Initialize the database
        watchedEpisodeRepository.saveAndFlush(watchedEpisode);

        int databaseSizeBeforeDelete = watchedEpisodeRepository.findAll().size();

        // Get the watchedEpisode
        restWatchedEpisodeMockMvc.perform(delete("/api/watched-episodes/{id}", watchedEpisode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WatchedEpisode> watchedEpisodeList = watchedEpisodeRepository.findAll();
        assertThat(watchedEpisodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchedEpisode.class);
        WatchedEpisode watchedEpisode1 = new WatchedEpisode();
        watchedEpisode1.setId(1L);
        WatchedEpisode watchedEpisode2 = new WatchedEpisode();
        watchedEpisode2.setId(watchedEpisode1.getId());
        assertThat(watchedEpisode1).isEqualTo(watchedEpisode2);
        watchedEpisode2.setId(2L);
        assertThat(watchedEpisode1).isNotEqualTo(watchedEpisode2);
        watchedEpisode1.setId(null);
        assertThat(watchedEpisode1).isNotEqualTo(watchedEpisode2);
    }
}
