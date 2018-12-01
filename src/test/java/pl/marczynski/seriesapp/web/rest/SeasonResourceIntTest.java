package pl.marczynski.seriesapp.web.rest;

import pl.marczynski.seriesapp.SeriesappApp;

import pl.marczynski.seriesapp.domain.Season;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.repository.SeasonRepository;
import pl.marczynski.seriesapp.service.SeasonService;
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
import pl.marczynski.seriesapp.web.rest.jhipster.TestUtil;

import javax.persistence.EntityManager;
import java.util.List;


import static pl.marczynski.seriesapp.web.rest.jhipster.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeasonResource REST controller.
 *
 * @see SeasonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeriesappApp.class)
public class SeasonResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_RELEASE_YEAR = 1926;
    private static final Integer UPDATED_RELEASE_YEAR = 1927;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeasonMockMvc;

    private Season season;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeasonResource seasonResource = new SeasonResource(seasonService);
        this.restSeasonMockMvc = MockMvcBuilders.standaloneSetup(seasonResource)
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
    public static Season createEntity(EntityManager em) {
        Season season = new Season()
            .number(DEFAULT_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .releaseYear(DEFAULT_RELEASE_YEAR);
        // Add required entity
        Series series = SeriesResourceIntTest.createEntity(em);
        em.persist(series);
        em.flush();
        season.setSeries(series);
        return season;
    }

    @Before
    public void initTest() {
        season = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeason() throws Exception {
        int databaseSizeBeforeCreate = seasonRepository.findAll().size();

        // Create the Season
        restSeasonMockMvc.perform(post("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isCreated());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeCreate + 1);
        Season testSeason = seasonList.get(seasonList.size() - 1);
        assertThat(testSeason.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSeason.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSeason.getReleaseYear()).isEqualTo(DEFAULT_RELEASE_YEAR);
    }

    @Test
    @Transactional
    public void createSeasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seasonRepository.findAll().size();

        // Create the Season with an existing ID
        season.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeasonMockMvc.perform(post("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isBadRequest());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = seasonRepository.findAll().size();
        // set the field null
        season.setNumber(null);

        // Create the Season, which fails.

        restSeasonMockMvc.perform(post("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isBadRequest());

        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReleaseYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = seasonRepository.findAll().size();
        // set the field null
        season.setReleaseYear(null);

        // Create the Season, which fails.

        restSeasonMockMvc.perform(post("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isBadRequest());

        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeasons() throws Exception {
        // Initialize the database
        seasonRepository.saveAndFlush(season);

        // Get all the seasonList
        restSeasonMockMvc.perform(get("/api/seasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(season.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].releaseYear").value(hasItem(DEFAULT_RELEASE_YEAR)));
    }
    
    @Test
    @Transactional
    public void getSeason() throws Exception {
        // Initialize the database
        seasonRepository.saveAndFlush(season);

        // Get the season
        restSeasonMockMvc.perform(get("/api/seasons/{id}", season.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(season.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.releaseYear").value(DEFAULT_RELEASE_YEAR));
    }

    @Test
    @Transactional
    public void getNonExistingSeason() throws Exception {
        // Get the season
        restSeasonMockMvc.perform(get("/api/seasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeason() throws Exception {
        // Initialize the database
        seasonRepository.saveAndFlush(season);

        int databaseSizeBeforeUpdate = seasonRepository.findAll().size();

        // Update the season
        Season updatedSeason = seasonRepository.findById(season.getId()).get();
        // Disconnect from session so that the updates on updatedSeason are not directly saved in db
        em.detach(updatedSeason);
        updatedSeason
            .number(UPDATED_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .releaseYear(UPDATED_RELEASE_YEAR);

        restSeasonMockMvc.perform(put("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeason)))
            .andExpect(status().isOk());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeUpdate);
        Season testSeason = seasonList.get(seasonList.size() - 1);
        assertThat(testSeason.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSeason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeason.getReleaseYear()).isEqualTo(UPDATED_RELEASE_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingSeason() throws Exception {
        int databaseSizeBeforeUpdate = seasonRepository.findAll().size();

        // Create the Season

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeasonMockMvc.perform(put("/api/seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(season)))
            .andExpect(status().isBadRequest());

        // Validate the Season in the database
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeason() throws Exception {
        // Initialize the database
        seasonRepository.saveAndFlush(season);

        int databaseSizeBeforeDelete = seasonRepository.findAll().size();

        // Get the season
        restSeasonMockMvc.perform(delete("/api/seasons/{id}", season.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Season> seasonList = seasonRepository.findAll();
        assertThat(seasonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Season.class);
        Season season1 = new Season();
        season1.setId(1L);
        Season season2 = new Season();
        season2.setId(season1.getId());
        assertThat(season1).isEqualTo(season2);
        season2.setId(2L);
        assertThat(season1).isNotEqualTo(season2);
        season1.setId(null);
        assertThat(season1).isNotEqualTo(season2);
    }
}
