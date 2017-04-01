package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.AgentService;
import com.ss.goldentown.domain.Agent;
import com.ss.goldentown.repository.AgentRepository;
import com.ss.goldentown.repository.search.AgentSearchRepository;
import com.ss.goldentown.service.dto.AgentDTO;
import com.ss.goldentown.service.mapper.AgentMapper;
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
 * Service Implementation for managing Agent.
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService{

    private final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);
    
    @Inject
    private AgentRepository agentRepository;

    @Inject
    private AgentMapper agentMapper;

    @Inject
    private AgentSearchRepository agentSearchRepository;

    /**
     * Save a agent.
     *
     * @param agentDTO the entity to save
     * @return the persisted entity
     */
    public AgentDTO save(AgentDTO agentDTO) {
        log.debug("Request to save Agent : {}", agentDTO);
        Agent agent = agentMapper.agentDTOToAgent(agentDTO);
        agent = agentRepository.save(agent);
        AgentDTO result = agentMapper.agentToAgentDTO(agent);
        agentSearchRepository.save(agent);
        return result;
    }

    /**
     *  Get all the agents.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AgentDTO> findAll() {
        log.debug("Request to get all Agents");
        List<AgentDTO> result = agentRepository.findAll().stream()
            .map(agentMapper::agentToAgentDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }
    
    /**
     *  Get all the agents.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AgentDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Agents");
        List<AgentDTO> result = agentRepository.findByUserIsCurrentUser().stream()
            .map(agentMapper::agentToAgentDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one agent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AgentDTO findOne(Long id) {
        log.debug("Request to get Agent : {}", id);
        Agent agent = agentRepository.findOne(id);
        AgentDTO agentDTO = agentMapper.agentToAgentDTO(agent);
        return agentDTO;
    }

    /**
     *  Delete the  agent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.delete(id);
        agentSearchRepository.delete(id);
    }

    /**
     * Search for the agent corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgentDTO> search(String query) {
        log.debug("Request to search Agents for query {}", query);
        return StreamSupport
            .stream(agentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(agentMapper::agentToAgentDTO)
            .collect(Collectors.toList());
    }
}
