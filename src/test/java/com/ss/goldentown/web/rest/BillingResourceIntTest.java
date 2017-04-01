package com.ss.goldentown.web.rest;

import com.ss.goldentown.Goldentown2App;

import com.ss.goldentown.domain.Billing;
import com.ss.goldentown.repository.BillingRepository;
import com.ss.goldentown.service.BillingService;
import com.ss.goldentown.repository.search.BillingSearchRepository;
import com.ss.goldentown.service.dto.BillingDTO;
import com.ss.goldentown.service.mapper.BillingMapper;

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
 * Test class for the BillingResource REST controller.
 *
 * @see BillingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goldentown2App.class)
public class BillingResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private BillingRepository billingRepository;

    @Inject
    private BillingMapper billingMapper;

    @Inject
    private BillingService billingService;

    @Inject
    private BillingSearchRepository billingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBillingMockMvc;

    private Billing billing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillingResource billingResource = new BillingResource();
        ReflectionTestUtils.setField(billingResource, "billingService", billingService);
        this.restBillingMockMvc = MockMvcBuilders.standaloneSetup(billingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billing createEntity(EntityManager em) {
        Billing billing = new Billing()
                .price(DEFAULT_PRICE)
                .time(DEFAULT_TIME);
        return billing;
    }

    @Before
    public void initTest() {
        billingSearchRepository.deleteAll();
        billing = createEntity(em);
    }

    @Test
    @Transactional
    public void createBilling() throws Exception {
        int databaseSizeBeforeCreate = billingRepository.findAll().size();

        // Create the Billing
        BillingDTO billingDTO = billingMapper.billingToBillingDTO(billing);

        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingDTO)))
            .andExpect(status().isCreated());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeCreate + 1);
        Billing testBilling = billingList.get(billingList.size() - 1);
        assertThat(testBilling.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBilling.getTime()).isEqualTo(DEFAULT_TIME);

        // Validate the Billing in ElasticSearch
        Billing billingEs = billingSearchRepository.findOne(testBilling.getId());
        assertThat(billingEs).isEqualToComparingFieldByField(testBilling);
    }

    @Test
    @Transactional
    public void createBillingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingRepository.findAll().size();

        // Create the Billing with an existing ID
        Billing existingBilling = new Billing();
        existingBilling.setId(1L);
        BillingDTO existingBillingDTO = billingMapper.billingToBillingDTO(existingBilling);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBillingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeCreate);
    }

   /* @Test
    @Transactional
    public void getAllBillings() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);

        // Get all the billingList
        restBillingMockMvc.perform(get("/api/billings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billing.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }*/

    @Test
    @Transactional
    public void getBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);

        // Get the billing
        restBillingMockMvc.perform(get("/api/billings/{id}", billing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billing.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBilling() throws Exception {
        // Get the billing
        restBillingMockMvc.perform(get("/api/billings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);
        billingSearchRepository.save(billing);
        int databaseSizeBeforeUpdate = billingRepository.findAll().size();

        // Update the billing
        Billing updatedBilling = billingRepository.findOne(billing.getId());
        updatedBilling
                .price(UPDATED_PRICE)
                .time(UPDATED_TIME);
        BillingDTO billingDTO = billingMapper.billingToBillingDTO(updatedBilling);

        restBillingMockMvc.perform(put("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingDTO)))
            .andExpect(status().isOk());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeUpdate);
        Billing testBilling = billingList.get(billingList.size() - 1);
        assertThat(testBilling.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBilling.getTime()).isEqualTo(UPDATED_TIME);

        // Validate the Billing in ElasticSearch
        Billing billingEs = billingSearchRepository.findOne(testBilling.getId());
        assertThat(billingEs).isEqualToComparingFieldByField(testBilling);
    }

    @Test
    @Transactional
    public void updateNonExistingBilling() throws Exception {
        int databaseSizeBeforeUpdate = billingRepository.findAll().size();

        // Create the Billing
        BillingDTO billingDTO = billingMapper.billingToBillingDTO(billing);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBillingMockMvc.perform(put("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingDTO)))
            .andExpect(status().isCreated());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);
        billingSearchRepository.save(billing);
        int databaseSizeBeforeDelete = billingRepository.findAll().size();

        // Get the billing
        restBillingMockMvc.perform(delete("/api/billings/{id}", billing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean billingExistsInEs = billingSearchRepository.exists(billing.getId());
        assertThat(billingExistsInEs).isFalse();

        // Validate the database is empty
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);
        billingSearchRepository.save(billing);

        // Search the billing
        restBillingMockMvc.perform(get("/api/_search/billings?query=id:" + billing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billing.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }
}
