package pl.marczynski.seriesapp.web.rest;

import pl.marczynski.seriesapp.SeriesappApp;

import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.repository.FollowedSeriesRepository;
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
 * Test class for the FollowedSeriesResource REST controller.
 *
 * @see FollowedSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeriesappApp.class)
public class FollowedSeriesResourceIntTest {

    private static final Rate DEFAULT_RATE = Rate.BAD;
    private static final Rate UPDATED_RATE = Rate.MEDIOCRE;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private FollowedSeriesRepository followedSeriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFollowedSeriesMockMvc;

    private FollowedSeries followedSeries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FollowedSeriesResource followedSeriesResource = new FollowedSeriesResource(followedSeriesRepository);
        this.restFollowedSeriesMockMvc = MockMvcBuilders.standaloneSetup(followedSeriesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FollowedSeries createEntity(EntityManager em) {
        FollowedSeries followedSeries = new FollowedSeries()
            .rate(DEFAULT_RATE)
            .comment(DEFAULT_COMMENT);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        followedSeries.setUser(user);
        // Add required entity
        Series series = SeriesResourceIntTest.createEntity(em);
        em.persist(series);
        em.flush();
        followedSeries.setSeries(series);
        return followedSeries;
    }

    @Before
    public void initTest() {
        followedSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createFollowedSeries() throws Exception {
        int databaseSizeBeforeCreate = followedSeriesRepository.findAll().size();

        // Create the FollowedSeries
        restFollowedSeriesMockMvc.perform(post("/api/followed-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followedSeries)))
            .andExpect(status().isCreated());

        // Validate the FollowedSeries in the database
        List<FollowedSeries> followedSeriesList = followedSeriesRepository.findAll();
        assertThat(followedSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        FollowedSeries testFollowedSeries = followedSeriesList.get(followedSeriesList.size() - 1);
        assertThat(testFollowedSeries.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testFollowedSeries.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createFollowedSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followedSeriesRepository.findAll().size();

        // Create the FollowedSeries with an existing ID
        followedSeries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowedSeriesMockMvc.perform(post("/api/followed-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followedSeries)))
            .andExpect(status().isBadRequest());

        // Validate the FollowedSeries in the database
        List<FollowedSeries> followedSeriesList = followedSeriesRepository.findAll();
        assertThat(followedSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFollowedSeries() throws Exception {
        // Initialize the database
        followedSeriesRepository.saveAndFlush(followedSeries);

        // Get all the followedSeriesList
        restFollowedSeriesMockMvc.perform(get("/api/followed-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(followedSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getFollowedSeries() throws Exception {
        // Initialize the database
        followedSeriesRepository.saveAndFlush(followedSeries);

        // Get the followedSeries
        restFollowedSeriesMockMvc.perform(get("/api/followed-series/{id}", followedSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(followedSeries.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFollowedSeries() throws Exception {
        // Get the followedSeries
        restFollowedSeriesMockMvc.perform(get("/api/followed-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollowedSeries() throws Exception {
        // Initialize the database
        followedSeriesRepository.saveAndFlush(followedSeries);

        int databaseSizeBeforeUpdate = followedSeriesRepository.findAll().size();

        // Update the followedSeries
        FollowedSeries updatedFollowedSeries = followedSeriesRepository.findById(followedSeries.getId()).get();
        // Disconnect from session so that the updates on updatedFollowedSeries are not directly saved in db
        em.detach(updatedFollowedSeries);
        updatedFollowedSeries
            .rate(UPDATED_RATE)
            .comment(UPDATED_COMMENT);

        restFollowedSeriesMockMvc.perform(put("/api/followed-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFollowedSeries)))
            .andExpect(status().isOk());

        // Validate the FollowedSeries in the database
        List<FollowedSeries> followedSeriesList = followedSeriesRepository.findAll();
        assertThat(followedSeriesList).hasSize(databaseSizeBeforeUpdate);
        FollowedSeries testFollowedSeries = followedSeriesList.get(followedSeriesList.size() - 1);
        assertThat(testFollowedSeries.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testFollowedSeries.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingFollowedSeries() throws Exception {
        int databaseSizeBeforeUpdate = followedSeriesRepository.findAll().size();

        // Create the FollowedSeries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowedSeriesMockMvc.perform(put("/api/followed-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followedSeries)))
            .andExpect(status().isBadRequest());

        // Validate the FollowedSeries in the database
        List<FollowedSeries> followedSeriesList = followedSeriesRepository.findAll();
        assertThat(followedSeriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFollowedSeries() throws Exception {
        // Initialize the database
        followedSeriesRepository.saveAndFlush(followedSeries);

        int databaseSizeBeforeDelete = followedSeriesRepository.findAll().size();

        // Get the followedSeries
        restFollowedSeriesMockMvc.perform(delete("/api/followed-series/{id}", followedSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FollowedSeries> followedSeriesList = followedSeriesRepository.findAll();
        assertThat(followedSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FollowedSeries.class);
        FollowedSeries followedSeries1 = new FollowedSeries();
        followedSeries1.setId(1L);
        FollowedSeries followedSeries2 = new FollowedSeries();
        followedSeries2.setId(followedSeries1.getId());
        assertThat(followedSeries1).isEqualTo(followedSeries2);
        followedSeries2.setId(2L);
        assertThat(followedSeries1).isNotEqualTo(followedSeries2);
        followedSeries1.setId(null);
        assertThat(followedSeries1).isNotEqualTo(followedSeries2);
    }
}
