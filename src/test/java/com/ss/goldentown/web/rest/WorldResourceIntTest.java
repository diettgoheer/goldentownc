package com.ss.goldentown.web.rest;

import com.ss.goldentown.Goldentown2App;

import com.ss.goldentown.domain.World;
import com.ss.goldentown.repository.WorldRepository;
import com.ss.goldentown.service.WorldService;
import com.ss.goldentown.repository.search.WorldSearchRepository;

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
 * Test class for the WorldResource REST controller.
 *
 * @see WorldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goldentown2App.class)
public class WorldResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PERSON_COUNT = 1D;
    private static final Double UPDATED_PERSON_COUNT = 2D;

    private static final Double DEFAULT_BIRTH_COUNT = 1D;
    private static final Double UPDATED_BIRTH_COUNT = 2D;

    private static final Double DEFAULT_DEATH_COUNT = 1D;
    private static final Double UPDATED_DEATH_COUNT = 2D;

    private static final Double DEFAULT_MAX_GENERATION = 1D;
    private static final Double UPDATED_MAX_GENERATION = 2D;

    private static final Double DEFAULT_AVERAGE_VALUE = 1D;
    private static final Double UPDATED_AVERAGE_VALUE = 2D;

    private static final Double DEFAULT_AVERAGE_AGE = 1D;
    private static final Double UPDATED_AVERAGE_AGE = 2D;

    private static final Double DEFAULT_MAX_VALUE = 1D;
    private static final Double UPDATED_MAX_VALUE = 2D;

    private static final Double DEFAULT_MAX_AGE = 1D;
    private static final Double UPDATED_MAX_AGE = 2D;

    private static final Double DEFAULT_WORLD_VALUE = 1D;
    private static final Double UPDATED_WORLD_VALUE = 2D;

    private static final Double DEFAULT_WORLD_AGE = 1D;
    private static final Double UPDATED_WORLD_AGE = 2D;

    private static final Double DEFAULT_MID_AGE = 1D;
    private static final Double UPDATED_MID_AGE = 2D;

    private static final Double DEFAULT_BASE_VALUE = 1D;
    private static final Double UPDATED_BASE_VALUE = 2D;

    private static final Double DEFAULT_GROW_RATE = 1D;
    private static final Double UPDATED_GROW_RATE = 2D;

    private static final Double DEFAULT_LEGACY_RATE = 1D;
    private static final Double UPDATED_LEGACY_RATE = 2D;

    private static final Double DEFAULT_BREED_RATE = 1D;
    private static final Double UPDATED_BREED_RATE = 2D;

    @Inject
    private WorldRepository worldRepository;

    @Inject
    private WorldService worldService;

    @Inject
    private WorldSearchRepository worldSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorldMockMvc;

    private World world;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorldResource worldResource = new WorldResource();
        ReflectionTestUtils.setField(worldResource, "worldService", worldService);
        this.restWorldMockMvc = MockMvcBuilders.standaloneSetup(worldResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static World createEntity(EntityManager em) {
        World world = new World()
                .time(DEFAULT_TIME)
                .personCount(DEFAULT_PERSON_COUNT)
                .birthCount(DEFAULT_BIRTH_COUNT)
                .deathCount(DEFAULT_DEATH_COUNT)
                .maxGeneration(DEFAULT_MAX_GENERATION)
                .averageValue(DEFAULT_AVERAGE_VALUE)
                .averageAge(DEFAULT_AVERAGE_AGE)
                .maxValue(DEFAULT_MAX_VALUE)
                .maxAge(DEFAULT_MAX_AGE)
                .worldValue(DEFAULT_WORLD_VALUE)
                .worldAge(DEFAULT_WORLD_AGE)
                .midAge(DEFAULT_MID_AGE)
                .baseValue(DEFAULT_BASE_VALUE)
                .growRate(DEFAULT_GROW_RATE)
                .legacyRate(DEFAULT_LEGACY_RATE)
                .breedRate(DEFAULT_BREED_RATE);
        return world;
    }

    @Before
    public void initTest() {
        worldSearchRepository.deleteAll();
        world = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorld() throws Exception {
        int databaseSizeBeforeCreate = worldRepository.findAll().size();

        // Create the World

        restWorldMockMvc.perform(post("/api/worlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(world)))
            .andExpect(status().isCreated());

        // Validate the World in the database
        List<World> worldList = worldRepository.findAll();
        assertThat(worldList).hasSize(databaseSizeBeforeCreate + 1);
        World testWorld = worldList.get(worldList.size() - 1);
        assertThat(testWorld.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testWorld.getPersonCount()).isEqualTo(DEFAULT_PERSON_COUNT);
        assertThat(testWorld.getBirthCount()).isEqualTo(DEFAULT_BIRTH_COUNT);
        assertThat(testWorld.getDeathCount()).isEqualTo(DEFAULT_DEATH_COUNT);
        assertThat(testWorld.getMaxGeneration()).isEqualTo(DEFAULT_MAX_GENERATION);
        assertThat(testWorld.getAverageValue()).isEqualTo(DEFAULT_AVERAGE_VALUE);
        assertThat(testWorld.getAverageAge()).isEqualTo(DEFAULT_AVERAGE_AGE);
        assertThat(testWorld.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testWorld.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
        assertThat(testWorld.getWorldValue()).isEqualTo(DEFAULT_WORLD_VALUE);
        assertThat(testWorld.getWorldAge()).isEqualTo(DEFAULT_WORLD_AGE);
        assertThat(testWorld.getMidAge()).isEqualTo(DEFAULT_MID_AGE);
        assertThat(testWorld.getBaseValue()).isEqualTo(DEFAULT_BASE_VALUE);
        assertThat(testWorld.getGrowRate()).isEqualTo(DEFAULT_GROW_RATE);
        assertThat(testWorld.getLegacyRate()).isEqualTo(DEFAULT_LEGACY_RATE);
        assertThat(testWorld.getBreedRate()).isEqualTo(DEFAULT_BREED_RATE);

        // Validate the World in ElasticSearch
        World worldEs = worldSearchRepository.findOne(testWorld.getId());
        assertThat(worldEs).isEqualToComparingFieldByField(testWorld);
    }

    @Test
    @Transactional
    public void createWorldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = worldRepository.findAll().size();

        // Create the World with an existing ID
        World existingWorld = new World();
        existingWorld.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorldMockMvc.perform(post("/api/worlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorld)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<World> worldList = worldRepository.findAll();
        assertThat(worldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorlds() throws Exception {
        // Initialize the database
        worldRepository.saveAndFlush(world);

        // Get all the worldList
        restWorldMockMvc.perform(get("/api/worlds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(world.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].personCount").value(hasItem(DEFAULT_PERSON_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].birthCount").value(hasItem(DEFAULT_BIRTH_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].deathCount").value(hasItem(DEFAULT_DEATH_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxGeneration").value(hasItem(DEFAULT_MAX_GENERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].averageValue").value(hasItem(DEFAULT_AVERAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].averageAge").value(hasItem(DEFAULT_AVERAGE_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].worldValue").value(hasItem(DEFAULT_WORLD_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].worldAge").value(hasItem(DEFAULT_WORLD_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].midAge").value(hasItem(DEFAULT_MID_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].baseValue").value(hasItem(DEFAULT_BASE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].growRate").value(hasItem(DEFAULT_GROW_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].legacyRate").value(hasItem(DEFAULT_LEGACY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].breedRate").value(hasItem(DEFAULT_BREED_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getWorld() throws Exception {
        // Initialize the database
        worldRepository.saveAndFlush(world);

        // Get the world
        restWorldMockMvc.perform(get("/api/worlds/{id}", world.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(world.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.personCount").value(DEFAULT_PERSON_COUNT.doubleValue()))
            .andExpect(jsonPath("$.birthCount").value(DEFAULT_BIRTH_COUNT.doubleValue()))
            .andExpect(jsonPath("$.deathCount").value(DEFAULT_DEATH_COUNT.doubleValue()))
            .andExpect(jsonPath("$.maxGeneration").value(DEFAULT_MAX_GENERATION.doubleValue()))
            .andExpect(jsonPath("$.averageValue").value(DEFAULT_AVERAGE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.averageAge").value(DEFAULT_AVERAGE_AGE.doubleValue()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.doubleValue()))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE.doubleValue()))
            .andExpect(jsonPath("$.worldValue").value(DEFAULT_WORLD_VALUE.doubleValue()))
            .andExpect(jsonPath("$.worldAge").value(DEFAULT_WORLD_AGE.doubleValue()))
            .andExpect(jsonPath("$.midAge").value(DEFAULT_MID_AGE.doubleValue()))
            .andExpect(jsonPath("$.baseValue").value(DEFAULT_BASE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.growRate").value(DEFAULT_GROW_RATE.doubleValue()))
            .andExpect(jsonPath("$.legacyRate").value(DEFAULT_LEGACY_RATE.doubleValue()))
            .andExpect(jsonPath("$.breedRate").value(DEFAULT_BREED_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorld() throws Exception {
        // Get the world
        restWorldMockMvc.perform(get("/api/worlds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorld() throws Exception {
        // Initialize the database
        worldService.save(world);

        int databaseSizeBeforeUpdate = worldRepository.findAll().size();

        // Update the world
        World updatedWorld = worldRepository.findOne(world.getId());
        updatedWorld
                .time(UPDATED_TIME)
                .personCount(UPDATED_PERSON_COUNT)
                .birthCount(UPDATED_BIRTH_COUNT)
                .deathCount(UPDATED_DEATH_COUNT)
                .maxGeneration(UPDATED_MAX_GENERATION)
                .averageValue(UPDATED_AVERAGE_VALUE)
                .averageAge(UPDATED_AVERAGE_AGE)
                .maxValue(UPDATED_MAX_VALUE)
                .maxAge(UPDATED_MAX_AGE)
                .worldValue(UPDATED_WORLD_VALUE)
                .worldAge(UPDATED_WORLD_AGE)
                .midAge(UPDATED_MID_AGE)
                .baseValue(UPDATED_BASE_VALUE)
                .growRate(UPDATED_GROW_RATE)
                .legacyRate(UPDATED_LEGACY_RATE)
                .breedRate(UPDATED_BREED_RATE);

        restWorldMockMvc.perform(put("/api/worlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorld)))
            .andExpect(status().isOk());

        // Validate the World in the database
        List<World> worldList = worldRepository.findAll();
        assertThat(worldList).hasSize(databaseSizeBeforeUpdate);
        World testWorld = worldList.get(worldList.size() - 1);
        assertThat(testWorld.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testWorld.getPersonCount()).isEqualTo(UPDATED_PERSON_COUNT);
        assertThat(testWorld.getBirthCount()).isEqualTo(UPDATED_BIRTH_COUNT);
        assertThat(testWorld.getDeathCount()).isEqualTo(UPDATED_DEATH_COUNT);
        assertThat(testWorld.getMaxGeneration()).isEqualTo(UPDATED_MAX_GENERATION);
        assertThat(testWorld.getAverageValue()).isEqualTo(UPDATED_AVERAGE_VALUE);
        assertThat(testWorld.getAverageAge()).isEqualTo(UPDATED_AVERAGE_AGE);
        assertThat(testWorld.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testWorld.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testWorld.getWorldValue()).isEqualTo(UPDATED_WORLD_VALUE);
        assertThat(testWorld.getWorldAge()).isEqualTo(UPDATED_WORLD_AGE);
        assertThat(testWorld.getMidAge()).isEqualTo(UPDATED_MID_AGE);
        assertThat(testWorld.getBaseValue()).isEqualTo(UPDATED_BASE_VALUE);
        assertThat(testWorld.getGrowRate()).isEqualTo(UPDATED_GROW_RATE);
        assertThat(testWorld.getLegacyRate()).isEqualTo(UPDATED_LEGACY_RATE);
        assertThat(testWorld.getBreedRate()).isEqualTo(UPDATED_BREED_RATE);

        // Validate the World in ElasticSearch
        World worldEs = worldSearchRepository.findOne(testWorld.getId());
        assertThat(worldEs).isEqualToComparingFieldByField(testWorld);
    }

    @Test
    @Transactional
    public void updateNonExistingWorld() throws Exception {
        int databaseSizeBeforeUpdate = worldRepository.findAll().size();

        // Create the World

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorldMockMvc.perform(put("/api/worlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(world)))
            .andExpect(status().isCreated());

        // Validate the World in the database
        List<World> worldList = worldRepository.findAll();
        assertThat(worldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorld() throws Exception {
        // Initialize the database
        worldService.save(world);

        int databaseSizeBeforeDelete = worldRepository.findAll().size();

        // Get the world
        restWorldMockMvc.perform(delete("/api/worlds/{id}", world.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean worldExistsInEs = worldSearchRepository.exists(world.getId());
        assertThat(worldExistsInEs).isFalse();

        // Validate the database is empty
        List<World> worldList = worldRepository.findAll();
        assertThat(worldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWorld() throws Exception {
        // Initialize the database
        worldService.save(world);

        // Search the world
        restWorldMockMvc.perform(get("/api/_search/worlds?query=id:" + world.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(world.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].personCount").value(hasItem(DEFAULT_PERSON_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].birthCount").value(hasItem(DEFAULT_BIRTH_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].deathCount").value(hasItem(DEFAULT_DEATH_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxGeneration").value(hasItem(DEFAULT_MAX_GENERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].averageValue").value(hasItem(DEFAULT_AVERAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].averageAge").value(hasItem(DEFAULT_AVERAGE_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].worldValue").value(hasItem(DEFAULT_WORLD_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].worldAge").value(hasItem(DEFAULT_WORLD_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].midAge").value(hasItem(DEFAULT_MID_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].baseValue").value(hasItem(DEFAULT_BASE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].growRate").value(hasItem(DEFAULT_GROW_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].legacyRate").value(hasItem(DEFAULT_LEGACY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].breedRate").value(hasItem(DEFAULT_BREED_RATE.doubleValue())));
    }
}
