package com.ss.goldentown.service;

import com.ss.goldentown.service.dto.DealDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Deal.
 */
public interface DealService {

    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    DealDTO save(DealDTO dealDTO);

    /**
     *  Get all the deals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<DealDTO> findAll();
    List<DealDTO> findByUserIsCurrentUser();
    /**
     *  Get the "id" deal.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DealDTO findOne(Long id);

    /**
     *  Delete the "id" deal.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the deal corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DealDTO> search(String query, Pageable pageable);
}
