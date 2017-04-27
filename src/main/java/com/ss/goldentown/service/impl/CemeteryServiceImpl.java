package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.CemeteryService;
import com.ss.goldentown.domain.Cemetery;
import com.ss.goldentown.repository.CemeteryRepository;
import com.ss.goldentown.repository.search.CemeterySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Cemetery.
 */
@Service
@Transactional
public class CemeteryServiceImpl implements CemeteryService{

    private final Logger log = LoggerFactory.getLogger(CemeteryServiceImpl.class);
    
    @Inject
    private CemeteryRepository cemeteryRepository;

    @Inject
    private CemeterySearchRepository cemeterySearchRepository;

    /**
     * Save a cemetery.
     *
     * @param cemetery the entity to save
     * @return the persisted entity
     */
    public Cemetery save(Cemetery cemetery) {
        log.debug("Request to save Cemetery : {}", cemetery);
        Cemetery result = cemeteryRepository.save(cemetery);
        cemeterySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the cemeteries.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Cemetery> findAll() {
        log.debug("Request to get all Cemeteries");
        List<Cemetery> result = cemeteryRepository.findAll();

        return result;
    }

    /**
     *  Get one cemetery by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cemetery findOne(Long id) {
        log.debug("Request to get Cemetery : {}", id);
        Cemetery cemetery = cemeteryRepository.findOne(id);
        return cemetery;
    }

    /**
     *  Delete the  cemetery by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cemetery : {}", id);
        cemeteryRepository.delete(id);
        cemeterySearchRepository.delete(id);
    }

    /**
     * Search for the cemetery corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Cemetery> search(String query) {
        log.debug("Request to search Cemeteries for query {}", query);
        return StreamSupport
            .stream(cemeterySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
