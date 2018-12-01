package pl.marczynski.seriesapp.web.rest;

import pl.marczynski.seriesapp.SeriesappApp;

import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.repository.SeriesRepository;
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
 * Test class for the SeriesResource REST controller.
 *
 * @see SeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeriesappApp.class)
public class SeriesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RELEASE_YEAR = 1926;
    private static final Integer UPDATED_RELEASE_YEAR = 1927;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeriesMockMvc;

    private Series series;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeriesResource seriesResource = new SeriesResource(seriesRepository);
        this.restSeriesMockMvc = MockMvcBuilders.standaloneSetup(seriesResource)
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
    public static Series createEntity(EntityManager em) {
        Series series = new Series()
            .name(DEFAULT_NAME)
            .releaseYear(DEFAULT_RELEASE_YEAR)
            .description(DEFAULT_DESCRIPTION);
        return series;
    }

    @Before
    public void initTest() {
        series = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeries() throws Exception {
        int databaseSizeBeforeCreate = seriesRepository.findAll().size();

        // Create the Series
        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isCreated());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeCreate + 1);
        Series testSeries = seriesList.get(seriesList.size() - 1);
        assertThat(testSeries.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSeries.getReleaseYear()).isEqualTo(DEFAULT_RELEASE_YEAR);
        assertThat(testSeries.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seriesRepository.findAll().size();

        // Create the Series with an existing ID
        series.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seriesRepository.findAll().size();
        // set the field null
        series.setName(null);

        // Create the Series, which fails.

        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReleaseYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = seriesRepository.findAll().size();
        // set the field null
        series.setReleaseYear(null);

        // Create the Series, which fails.

        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeries() throws Exception {
        // Initialize the database
        seriesRepository.saveAndFlush(series);

        // Get all the seriesList
        restSeriesMockMvc.perform(get("/api/series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(series.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].releaseYear").value(hasItem(DEFAULT_RELEASE_YEAR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSeries() throws Exception {
        // Initialize the database
        seriesRepository.saveAndFlush(series);

        // Get the series
        restSeriesMockMvc.perform(get("/api/series/{id}", series.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(series.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.releaseYear").value(DEFAULT_RELEASE_YEAR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeries() throws Exception {
        // Get the series
        restSeriesMockMvc.perform(get("/api/series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeries() throws Exception {
        // Initialize the database
        seriesRepository.saveAndFlush(series);

        int databaseSizeBeforeUpdate = seriesRepository.findAll().size();

        // Update the series
        Series updatedSeries = seriesRepository.findById(series.getId()).get();
        // Disconnect from session so that the updates on updatedSeries are not directly saved in db
        em.detach(updatedSeries);
        updatedSeries
            .name(UPDATED_NAME)
            .releaseYear(UPDATED_RELEASE_YEAR)
            .description(UPDATED_DESCRIPTION);

        restSeriesMockMvc.perform(put("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeries)))
            .andExpect(status().isOk());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeUpdate);
        Series testSeries = seriesList.get(seriesList.size() - 1);
        assertThat(testSeries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSeries.getReleaseYear()).isEqualTo(UPDATED_RELEASE_YEAR);
        assertThat(testSeries.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSeries() throws Exception {
        int databaseSizeBeforeUpdate = seriesRepository.findAll().size();

        // Create the Series

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeriesMockMvc.perform(put("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeries() throws Exception {
        // Initialize the database
        seriesRepository.saveAndFlush(series);

        int databaseSizeBeforeDelete = seriesRepository.findAll().size();

        // Get the series
        restSeriesMockMvc.perform(delete("/api/series/{id}", series.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Series.class);
        Series series1 = new Series();
        series1.setId(1L);
        Series series2 = new Series();
        series2.setId(series1.getId());
        assertThat(series1).isEqualTo(series2);
        series2.setId(2L);
        assertThat(series1).isNotEqualTo(series2);
        series1.setId(null);
        assertThat(series1).isNotEqualTo(series2);
    }
}
