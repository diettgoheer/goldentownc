package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.service.AgentService;
import com.ss.goldentown.web.rest.util.HeaderUtil;
import com.ss.goldentown.service.dto.AgentDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Agent.
 */
@RestController
@RequestMapping("/api")
public class AgentResource {

    private final Logger log = LoggerFactory.getLogger(AgentResource.class);
        
    @Inject
    private AgentService agentService;

    /**
     * POST  /agents : Create a new agent.
     *
     * @param agentDTO the agentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agentDTO, or with status 400 (Bad Request) if the agent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agents")
    @Timed
    public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) throws URISyntaxException {
        log.debug("REST request to save Agent : {}", agentDTO);
        if (agentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("agent", "idexists", "A new agent cannot already have an ID")).body(null);
        }
        AgentDTO result = agentService.save(agentDTO);
        return ResponseEntity.created(new URI("/api/agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("agent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agents : Updates an existing agent.
     *
     * @param agentDTO the agentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agentDTO,
     * or with status 400 (Bad Request) if the agentDTO is not valid,
     * or with status 500 (Internal Server Error) if the agentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agents")
    @Timed
    public ResponseEntity<AgentDTO> updateAgent(@RequestBody AgentDTO agentDTO) throws URISyntaxException {
        log.debug("REST request to update Agent : {}", agentDTO);
        if (agentDTO.getId() == null) {
            return createAgent(agentDTO);
        }
        AgentDTO result = agentService.save(agentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("agent", agentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agents : get all the agents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agents in body
     */
    @GetMapping("/agents")
    @Timed
    public List<AgentDTO> getAllAgents() {
        log.debug("REST request to get all Agents");
        return agentService.findByUserIsCurrentUser();
    }
    

    /**
     * GET  /agents/:id : get the "id" agent.
     *
     * @param id the id of the agentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agents/{id}")
    @Timed
    public ResponseEntity<AgentDTO> getAgent(@PathVariable Long id) {
        log.debug("REST request to get Agent : {}", id);
        AgentDTO agentDTO = agentService.findOne(id);
        return Optional.ofNullable(agentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /agents/:id : delete the "id" agent.
     *
     * @param id the id of the agentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agents/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        log.debug("REST request to delete Agent : {}", id);
        agentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("agent", id.toString())).build();
    }

    /**
     * SEARCH  /_search/agents?query=:query : search for the agent corresponding
     * to the query.
     *
     * @param query the query of the agent search 
     * @return the result of the search
     */
    @GetMapping("/_search/agents")
    @Timed
    public List<AgentDTO> searchAgents(@RequestParam String query) {
        log.debug("REST request to search Agents for query {}", query);
        return agentService.search(query);
    }


}
