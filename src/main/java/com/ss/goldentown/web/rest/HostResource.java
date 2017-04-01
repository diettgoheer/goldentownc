package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.service.HostService;
import com.ss.goldentown.web.rest.util.HeaderUtil;
import com.ss.goldentown.service.dto.HostDTO;

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
 * REST controller for managing Host.
 */
@RestController
@RequestMapping("/api")
public class HostResource {

    private final Logger log = LoggerFactory.getLogger(HostResource.class);
        
    @Inject
    private HostService hostService;

    /**
     * POST  /hosts : Create a new host.
     *
     * @param hostDTO the hostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hostDTO, or with status 400 (Bad Request) if the host has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hosts")
    @Timed
    public ResponseEntity<HostDTO> createHost(@RequestBody HostDTO hostDTO) throws URISyntaxException {
        log.debug("REST request to save Host : {}", hostDTO);
        if (hostDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("host", "idexists", "A new host cannot already have an ID")).body(null);
        }
        HostDTO result = hostService.save(hostDTO);
        return ResponseEntity.created(new URI("/api/hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("host", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hosts : Updates an existing host.
     *
     * @param hostDTO the hostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hostDTO,
     * or with status 400 (Bad Request) if the hostDTO is not valid,
     * or with status 500 (Internal Server Error) if the hostDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hosts")
    @Timed
    public ResponseEntity<HostDTO> updateHost(@RequestBody HostDTO hostDTO) throws URISyntaxException {
        log.debug("REST request to update Host : {}", hostDTO);
        if (hostDTO.getId() == null) {
            return createHost(hostDTO);
        }
        HostDTO result = hostService.save(hostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("host", hostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hosts : get all the hosts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hosts in body
     */
    @GetMapping("/hosts")
    @Timed
    public List<HostDTO> getAllHosts() {
        log.debug("REST request to get all Hosts");
        return hostService.findAll();
    }

    /**
     * GET  /hosts/:id : get the "id" host.
     *
     * @param id the id of the hostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hosts/{id}")
    @Timed
    public ResponseEntity<HostDTO> getHost(@PathVariable Long id) {
        log.debug("REST request to get Host : {}", id);
        HostDTO hostDTO = hostService.findOne(id);
        return Optional.ofNullable(hostDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hosts/:id : delete the "id" host.
     *
     * @param id the id of the hostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hosts/{id}")
    @Timed
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        log.debug("REST request to delete Host : {}", id);
        hostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("host", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hosts?query=:query : search for the host corresponding
     * to the query.
     *
     * @param query the query of the host search 
     * @return the result of the search
     */
    @GetMapping("/_search/hosts")
    @Timed
    public List<HostDTO> searchHosts(@RequestParam String query) {
        log.debug("REST request to search Hosts for query {}", query);
        return hostService.search(query);
    }


}
