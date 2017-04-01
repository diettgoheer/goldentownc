package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.BillingService;
import com.ss.goldentown.domain.Billing;
import com.ss.goldentown.repository.BillingRepository;
import com.ss.goldentown.repository.search.BillingSearchRepository;
import com.ss.goldentown.service.dto.BillingDTO;
import com.ss.goldentown.service.mapper.BillingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Billing.
 */
@Service
@Transactional
public class BillingServiceImpl implements BillingService{

    private final Logger log = LoggerFactory.getLogger(BillingServiceImpl.class);
    
    @Inject
    private BillingRepository billingRepository;

    @Inject
    private BillingMapper billingMapper;

    @Inject
    private BillingSearchRepository billingSearchRepository;

    /**
     * Save a billing.
     *
     * @param billingDTO the entity to save
     * @return the persisted entity
     */
    public BillingDTO save(BillingDTO billingDTO) {
        log.debug("Request to save Billing : {}", billingDTO);
        Billing billing = billingMapper.billingDTOToBilling(billingDTO);
        billing = billingRepository.save(billing);
        BillingDTO result = billingMapper.billingToBillingDTO(billing);
        billingSearchRepository.save(billing);
        return result;
    }

    /**
     *  Get all the billings.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BillingDTO> findAll() {
        log.debug("Request to get all Billings");
        List<BillingDTO> result = billingRepository.findAll().stream()
            .map(billingMapper::billingToBillingDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }
    /**
     *  Get all the billings.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BillingDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Billings");
        List<BillingDTO> result = billingRepository.findByUserIsCurrentUser().stream()
            .map(billingMapper::billingToBillingDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one billing by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public BillingDTO findOne(Long id) {
        log.debug("Request to get Billing : {}", id);
        Billing billing = billingRepository.findOne(id);
        BillingDTO billingDTO = billingMapper.billingToBillingDTO(billing);
        return billingDTO;
    }

    /**
     *  Delete the  billing by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Billing : {}", id);
        billingRepository.delete(id);
        billingSearchRepository.delete(id);
    }

    /**
     * Search for the billing corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BillingDTO> search(String query) {
        log.debug("Request to search Billings for query {}", query);
        return StreamSupport
            .stream(billingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(billingMapper::billingToBillingDTO)
            .collect(Collectors.toList());
    }
}
