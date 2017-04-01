package com.ss.goldentown.service;

import com.ss.goldentown.service.dto.BillingDTO;
import java.util.List;

/**
 * Service Interface for managing Billing.
 */
public interface BillingService {

    /**
     * Save a billing.
     *
     * @param billingDTO the entity to save
     * @return the persisted entity
     */
    BillingDTO save(BillingDTO billingDTO);

    /**
     *  Get all the billings.
     *  
     *  @return the list of entities
     */
    List<BillingDTO> findAll();
    List<BillingDTO> findByUserIsCurrentUser();
    /**
     *  Get the "id" billing.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BillingDTO findOne(Long id);

    /**
     *  Delete the "id" billing.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the billing corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<BillingDTO> search(String query);
}
