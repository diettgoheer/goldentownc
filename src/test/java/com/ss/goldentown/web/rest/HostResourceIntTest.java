package com.ss.goldentown.web.rest;

import com.ss.goldentown.Goldentown2App;

import com.ss.goldentown.domain.Host;
import com.ss.goldentown.repository.HostRepository;
import com.ss.goldentown.service.HostService;
import com.ss.goldentown.repository.search.HostSearchRepository;
import com.ss.goldentown.service.dto.HostDTO;
import com.ss.goldentown.service.mapper.HostMapper;

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

import com.ss.goldentown.domain.enumeration.HostType;
/**
 * Test class for the HostResource REST controller.
 *
 * @see HostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goldentown2App.class)
public class HostResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTHDAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTHDAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;

    private static final Double DEFAULT_CREDIT = 1D;
    private static final Double UPDATED_CREDIT = 2D;

    private static final HostType DEFAULT_TYPE = HostType.ENTERPRISE;
    private static final HostType UPDATED_TYPE = HostType.PERSON;

    @Inject
    private HostRepository hostRepository;

    @Inject
    private HostMapper hostMapper;

    @Inject
    private HostService hostService;

    @Inject
    private HostSearchRepository hostSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHostMockMvc;

    private Host host;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HostResource hostResource = new HostResource();
        ReflectionTestUtils.setField(hostResource, "hostService", hostService);
        this.restHostMockMvc = MockMvcBuilders.standaloneSetup(hostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Host createEntity(EntityManager em) {
        Host host = new Host()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .birthday(DEFAULT_BIRTHDAY)
                .telephone(DEFAULT_TELEPHONE)
                .temperature(DEFAULT_TEMPERATURE)
                .credit(DEFAULT_CREDIT)
                .type(DEFAULT_TYPE);
        return host;
    }

    @Before
    public void initTest() {
        hostSearchRepository.deleteAll();
        host = createEntity(em);
    }

    @Test
    @Transactional
    public void createHost() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host
        HostDTO hostDTO = hostMapper.hostToHostDTO(host);

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isCreated());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate + 1);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHost.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testHost.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testHost.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testHost.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testHost.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Host in ElasticSearch
        Host hostEs = hostSearchRepository.findOne(testHost.getId());
        assertThat(hostEs).isEqualToComparingFieldByField(testHost);
    }

    @Test
    @Transactional
    public void createHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host with an existing ID
        Host existingHost = new Host();
        existingHost.setId(1L);
        HostDTO existingHostDTO = hostMapper.hostToHostDTO(existingHost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHosts() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get all the hostList
        restHostMockMvc.perform(get("/api/hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(host.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", host.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(host.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.birthday").value(sameInstant(DEFAULT_BIRTHDAY)))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHost() throws Exception {
        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);
        hostSearchRepository.save(host);
        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Update the host
        Host updatedHost = hostRepository.findOne(host.getId());
        updatedHost
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .birthday(UPDATED_BIRTHDAY)
                .telephone(UPDATED_TELEPHONE)
                .temperature(UPDATED_TEMPERATURE)
                .credit(UPDATED_CREDIT)
                .type(UPDATED_TYPE);
        HostDTO hostDTO = hostMapper.hostToHostDTO(updatedHost);

        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isOk());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHost.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testHost.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testHost.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testHost.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testHost.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Host in ElasticSearch
        Host hostEs = hostSearchRepository.findOne(testHost.getId());
        assertThat(hostEs).isEqualToComparingFieldByField(testHost);
    }

    @Test
    @Transactional
    public void updateNonExistingHost() throws Exception {
        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Create the Host
        HostDTO hostDTO = hostMapper.hostToHostDTO(host);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isCreated());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);
        hostSearchRepository.save(host);
        int databaseSizeBeforeDelete = hostRepository.findAll().size();

        // Get the host
        restHostMockMvc.perform(delete("/api/hosts/{id}", host.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean hostExistsInEs = hostSearchRepository.exists(host.getId());
        assertThat(hostExistsInEs).isFalse();

        // Validate the database is empty
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);
        hostSearchRepository.save(host);

        // Search the host
        restHostMockMvc.perform(get("/api/_search/hosts?query=id:" + host.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(host.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
}
