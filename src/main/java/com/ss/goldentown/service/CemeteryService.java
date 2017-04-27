package com.ss.goldentown.service;

import com.ss.goldentown.domain.Cemetery;
import java.util.List;

/**
 * Service Interface for managing Cemetery.
 */
public interface CemeteryService {

    /**
     * Save a cemetery.
     *
     * @param cemetery the entity to save
     * @return the persisted entity
     */
    Cemetery save(Cemetery cemetery);

    /**
     *  Get all the cemeteries.
     *  
     *  @return the list of entities
     */
    List<Cemetery> findAll();

    /**
     *  Get the "id" cemetery.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cemetery findOne(Long id);

    /**
     *  Delete the "id" cemetery.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cemetery corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Cemetery> search(String query);
}
