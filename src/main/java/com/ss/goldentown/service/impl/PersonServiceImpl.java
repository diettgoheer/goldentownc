package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.PersonService;
import com.ss.goldentown.domain.Person;
import com.ss.goldentown.repository.PersonRepository;
import com.ss.goldentown.repository.search.PersonSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Person.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService{

//    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
	
    @Inject
    private PersonRepository personRepository;

    @Inject
    private PersonSearchRepository personSearchRepository;

    @PersistenceContext
    private EntityManager em;
    /**
     * Save a person.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    public Person save(Person person) {
     //   log.debug("Request to save Person : {}", person);
        Person result = personRepository.save(person);
        personSearchRepository.save(result);
        return result;
    }
    
    public void batchDelete(List<Person> list){
    	personRepository.deleteInBatch(list);
    }
    
    public void batchDeleteAll(){
    	personRepository.deleteAllInBatch();
    }
    
    public void batchInsert(List<Person> list) {
        for(int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if(i % 30== 0) {
                em.flush();
                em.clear();
            }
        }
    }
    
    public void batchUpdate(List<Person> list) {
        for(int i = 0; i < list.size(); i++) {
            em.merge(list.get(i));
            if(i % 30== 0) {
                em.flush();
                em.clear();
            }
        }
    }
    
    
    /**
     *  Get all the people.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Person> findAll() {
     //   log.debug("Request to get all People");
        List<Person> result = personRepository.findAllByIsDead(false);

        return result;
    }

    
    
    /**
     *  Get one person by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Person findOne(Long id) {
       // log.debug("Request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        return person;
    }

    /**
     *  Delete the  person by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
       // log.debug("Request to delete Person : {}", id);
        personRepository.delete(id);
        personSearchRepository.delete(id);
    }

    /**
     * Search for the person corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Person> search(String query) {
      //  log.debug("Request to search People for query {}", query);
        return StreamSupport
            .stream(personSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
