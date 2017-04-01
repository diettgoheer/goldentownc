package com.ss.goldentown.service;

import com.ss.goldentown.service.dto.AgentDTO;
import java.util.List;

/**
 * Service Interface for managing Agent.
 */
public interface AgentService {

    /**
     * Save a agent.
     *
     * @param agentDTO the entity to save
     * @return the persisted entity
     */
    AgentDTO save(AgentDTO agentDTO);

    /**
     *  Get all the agents.
     *  
     *  @return the list of entities
     */
    List<AgentDTO> findAll();
    List<AgentDTO> findByUserIsCurrentUser();
    /**
     *  Get the "id" agent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AgentDTO findOne(Long id);

    /**
     *  Delete the "id" agent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the agent corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AgentDTO> search(String query);
}
