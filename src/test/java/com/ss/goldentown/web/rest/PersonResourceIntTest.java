package com.ss.goldentown.web.rest;

import com.ss.goldentown.Goldentown2App;

import com.ss.goldentown.domain.Person;
import com.ss.goldentown.repository.PersonRepository;
import com.ss.goldentown.service.PersonService;
import com.ss.goldentown.repository.search.PersonSearchRepository;

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
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goldentown2App.class)
public class PersonResourceIntTest {

    private static final String DEFAULT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

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
    private PersonRepository personRepository;

    @Inject
    private PersonService personService;

    @Inject
    private PersonSearchRepository personSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personService", personService);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
                .person(DEFAULT_PERSON)
                .surname(DEFAULT_SURNAME)
                .name(DEFAULT_NAME)
                .fatherName(DEFAULT_FATHER_NAME)
                .age(DEFAULT_AGE)
                .generation(DEFAULT_GENERATION)
                .value(DEFAULT_VALUE)
                .lastValue(DEFAULT_LAST_VALUE)
                .birthday(DEFAULT_BIRTHDAY)
                .deathday(DEFAULT_DEATHDAY)
                .isDead(DEFAULT_IS_DEAD);
        return person;
    }

    @Before
    public void initTest() {
        personSearchRepository.deleteAll();
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getPerson()).isEqualTo(DEFAULT_PERSON);
        assertThat(testPerson.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testPerson.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPerson.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testPerson.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPerson.getLastValue()).isEqualTo(DEFAULT_LAST_VALUE);
        assertThat(testPerson.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testPerson.getDeathday()).isEqualTo(DEFAULT_DEATHDAY);
        assertThat(testPerson.isIsDead()).isEqualTo(DEFAULT_IS_DEAD);

        // Validate the Person in ElasticSearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToComparingFieldByField(testPerson);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        Person existingPerson = new Person();
        existingPerson.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPerson)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].person").value(hasItem(DEFAULT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
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
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.person").value(DEFAULT_PERSON.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
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
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personService.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        updatedPerson
                .person(UPDATED_PERSON)
                .surname(UPDATED_SURNAME)
                .name(UPDATED_NAME)
                .fatherName(UPDATED_FATHER_NAME)
                .age(UPDATED_AGE)
                .generation(UPDATED_GENERATION)
                .value(UPDATED_VALUE)
                .lastValue(UPDATED_LAST_VALUE)
                .birthday(UPDATED_BIRTHDAY)
                .deathday(UPDATED_DEATHDAY)
                .isDead(UPDATED_IS_DEAD);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getPerson()).isEqualTo(UPDATED_PERSON);
        assertThat(testPerson.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testPerson.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPerson.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testPerson.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPerson.getLastValue()).isEqualTo(UPDATED_LAST_VALUE);
        assertThat(testPerson.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPerson.getDeathday()).isEqualTo(UPDATED_DEATHDAY);
        assertThat(testPerson.isIsDead()).isEqualTo(UPDATED_IS_DEAD);

        // Validate the Person in ElasticSearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToComparingFieldByField(testPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personService.save(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean personExistsInEs = personSearchRepository.exists(person.getId());
        assertThat(personExistsInEs).isFalse();

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerson() throws Exception {
        // Initialize the database
        personService.save(person);

        // Search the person
        restPersonMockMvc.perform(get("/api/_search/people?query=id:" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].person").value(hasItem(DEFAULT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastValue").value(hasItem(DEFAULT_LAST_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].deathday").value(hasItem(sameInstant(DEFAULT_DEATHDAY))))
            .andExpect(jsonPath("$.[*].isDead").value(hasItem(DEFAULT_IS_DEAD.booleanValue())));
    }
}
