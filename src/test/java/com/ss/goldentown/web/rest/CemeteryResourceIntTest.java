package com.ss.goldentown.web.rest;

import com.ss.goldentown.Goldentown2App;

import com.ss.goldentown.domain.Cemetery;
import com.ss.goldentown.repository.CemeteryRepository;
import com.ss.goldentown.service.CemeteryService;
import com.ss.goldentown.repository.search.CemeterySearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ss.goldentown.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CemeteryResource REST controller.
 *
 * @see CemeteryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goldentown2App.class)
public class CemeteryResourceIntTest {

    private static final String DEFAULT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_PERSON = "BBBBBBBBBB";

    private static final Double DEFAULT_AGE = 1D;
    private static final Double UPDATED_AGE = 2D;

    private static final Double DEFAULT_GENERATION = 1D;
    private static final Double UPDATED_GENERATION = 2D;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Double DEFAULT_LAST_VALUE = 1D;
    private static final Double UPDATED_LAST_VALUE = 2D;

    private static final ZonedDateTime DEFAULT_BIRTHDAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTHDAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DEATHDAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEATHDAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_DEAD = false;
    private static final Boolean UPDATED_IS_DEAD = true;

    @Inject
    private CemeteryRepository cemeteryRepository;

    @Inject
    private CemeteryService cemeteryService;

    @Inject
    private CemeterySearchRepository cemeterySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCemeteryMockMvc;

    private Cemetery cemetery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CemeteryResource cemeteryResource = new CemeteryResource();
        ReflectionTestUtils.setField(cemeteryResource, "cemeteryService", cemeteryService);
        this.restCemeteryMockMvc = MockMvcBuilders.standaloneSetup(cemeteryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cemetery createEntity(EntityManager em) {
        Cemetery cemetery = new Cemetery()
                .person(DEFAULT_PERSON)
                .age(DEFAULT_AGE)
                .generation(DEFAULT_GENERATION)
                .value(DEFAULT_VALUE)
                .lastValue(DEFAULT_LAST_VALUE)
                .birthday(DEFAULT_BIRTHDAY)
                .deathday(DEFAULT_DEATHDAY)
                .isDead(DEFAULT_IS_DEAD);
        return cemetery;
    }

    @Before
    public void initTest() {
        cemeterySearchRepository.deleteAll();
        cemetery = createEntity(em);
    }

    @Test
    @Transactional
    public void createCemetery() throws Exception {
        int databaseSizeBeforeCreate = cemeteryRepository.findAll().size();

        // Create the Cemetery

        restCemeteryMockMvc.perform(post("/api/cemeteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cemetery)))
            .andExpect(status().isCreated());

        // Validate the Cemetery in the database
        List<Cemetery> cemeteryList = cemeteryRepository.findAll();
        assertThat(cemeteryList).hasSize(databaseSizeBeforeCreate + 1);
        Cemetery testCemetery = cemeteryList.get(cemeteryList.size() - 1);
        assertThat(testCemetery.getPerson()).isEqualTo(DEFAULT_PERSON);
        assertThat(testCemetery.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCemetery.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testCemetery.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCemetery.getLastValue()).isEqualTo(DEFAULT_LAST_VALUE);
        assertThat(testCemetery.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testCemetery.getDeathday()).isEqualTo(DEFAULT_DEATHDAY);
        assertThat(testCemetery.isIsDead()).isEqualTo(DEFAULT_IS_DEAD);

        // Validate the Cemetery in ElasticSearch
        Cemetery cemeteryEs = cemeterySearchRepository.findOne(testCemetery.getId());
        assertThat(cemeteryEs).isEqualToComparingFieldByField(testCemetery);
    }

    @Test
    @Transactional
    public void createCemeteryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cemeteryRepository.findAll().size();

        // Create the Cemetery with an existing ID
        Cemetery existingCemetery = new Cemetery();
        existingCemetery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCemeteryMockMvc.perform(post("/api/cemeteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCemetery)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Cemetery> cemeteryList = cemeteryRepository.findAll();
        assertThat(cemeteryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCemeteries() throws Exception {
        // Initialize the database
        cemeteryRepository.saveAndFlush(cemetery);

        // Get all the cemeteryList
        restCemeteryMockMvc.perform(get("/api/cemeteries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cemetery.getId().intValue())))
            .andExpect(jsonPath("$.[*].person").value(hasItem(DEFAULT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastValue").value(hasItem(DEFAULT_LAST_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].deathday").value(hasItem(sameInstant(DEFAULT_DEATHDAY))))
            .andExpect(jsonPath("$.[*].isDead").value(hasItem(DEFAULT_IS_DEAD.booleanValue())));
    }

    @Test
    @Transactional
    public void getCemetery() throws Exception {
        // Initialize the database
        cemeteryRepository.saveAndFlush(cemetery);

        // Get the cemetery
        restCemeteryMockMvc.perform(get("/api/cemeteries/{id}", cemetery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cemetery.getId().intValue()))
            .andExpect(jsonPath("$.person").value(DEFAULT_PERSON.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.doubleValue()))
            .andExpect(jsonPath("$.generation").value(DEFAULT_GENERATION.doubleValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.lastValue").value(DEFAULT_LAST_VALUE.doubleValue()))
            .andExpect(jsonPath("$.birthday").value(sameInstant(DEFAULT_BIRTHDAY)))
            .andExpect(jsonPath("$.deathday").value(sameInstant(DEFAULT_DEATHDAY)))
            .andExpect(jsonPath("$.isDead").value(DEFAULT_IS_DEAD.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCemetery() throws Exception {
        // Get the cemetery
        restCemeteryMockMvc.perform(get("/api/cemeteries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCemetery() throws Exception {
        // Initialize the database
        cemeteryService.save(cemetery);

        int databaseSizeBeforeUpdate = cemeteryRepository.findAll().size();

        // Update the cemetery
        Cemetery updatedCemetery = cemeteryRepository.findOne(cemetery.getId());
        updatedCemetery
                .person(UPDATED_PERSON)
                .age(UPDATED_AGE)
                .generation(UPDATED_GENERATION)
                .value(UPDATED_VALUE)
                .lastValue(UPDATED_LAST_VALUE)
                .birthday(UPDATED_BIRTHDAY)
                .deathday(UPDATED_DEATHDAY)
                .isDead(UPDATED_IS_DEAD);

        restCemeteryMockMvc.perform(put("/api/cemeteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCemetery)))
            .andExpect(status().isOk());

        // Validate the Cemetery in the database
        List<Cemetery> cemeteryList = cemeteryRepository.findAll();
        assertThat(cemeteryList).hasSize(databaseSizeBeforeUpdate);
        Cemetery testCemetery = cemeteryList.get(cemeteryList.size() - 1);
        assertThat(testCemetery.getPerson()).isEqualTo(UPDATED_PERSON);
        assertThat(testCemetery.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCemetery.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testCemetery.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCemetery.getLastValue()).isEqualTo(UPDATED_LAST_VALUE);
        assertThat(testCemetery.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCemetery.getDeathday()).isEqualTo(UPDATED_DEATHDAY);
        assertThat(testCemetery.isIsDead()).isEqualTo(UPDATED_IS_DEAD);

        // Validate the Cemetery in ElasticSearch
        Cemetery cemeteryEs = cemeterySearchRepository.findOne(testCemetery.getId());
        assertThat(cemeteryEs).isEqualToComparingFieldByField(testCemetery);
    }

    @Test
    @Transactional
    public void updateNonExistingCemetery() throws Exception {
        int databaseSizeBeforeUpdate = cemeteryRepository.findAll().size();

        // Create the Cemetery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCemeteryMockMvc.perform(put("/api/cemeteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cemetery)))
            .andExpect(status().isCreated());

        // Validate the Cemetery in the database
        List<Cemetery> cemeteryList = cemeteryRepository.findAll();
        assertThat(cemeteryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCemetery() throws Exception {
        // Initialize the database
        cemeteryService.save(cemetery);

        int databaseSizeBeforeDelete = cemeteryRepository.findAll().size();

        // Get the cemetery
        restCemeteryMockMvc.perform(delete("/api/cemeteries/{id}", cemetery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean cemeteryExistsInEs = cemeterySearchRepository.exists(cemetery.getId());
        assertThat(cemeteryExistsInEs).isFalse();

        // Validate the database is empty
        List<Cemetery> cemeteryList = cemeteryRepository.findAll();
        assertThat(cemeteryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCemetery() throws Exception {
        // Initialize the database
        cemeteryService.save(cemetery);

        // Search the cemetery
        restCemeteryMockMvc.perform(get("/api/_search/cemeteries?query=id:" + cemetery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cemetery.getId().intValue())))
            .andExpect(jsonPath("$.[*].person").value(hasItem(DEFAULT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastValue").value(hasItem(DEFAULT_LAST_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].deathday").value(hasItem(sameInstant(DEFAULT_DEATHDAY))))
            .andExpect(jsonPath("$.[*].isDead").value(hasItem(DEFAULT_IS_DEAD.booleanValue())));
    }
}
