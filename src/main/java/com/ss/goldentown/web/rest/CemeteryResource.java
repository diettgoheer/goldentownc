package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.domain.Cemetery;
import com.ss.goldentown.service.CemeteryService;
import com.ss.goldentown.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cemetery.
 */
@RestController
@RequestMapping("/api")
public class CemeteryResource {

    private final Logger log = LoggerFactory.getLogger(CemeteryResource.class);
        
    @Inject
    private CemeteryService cemeteryService;

    /**
     * POST  /cemeteries : Create a new cemetery.
     *
     * @param cemetery the cemetery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cemetery, or with status 400 (Bad Request) if the cemetery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cemeteries")
    @Timed
    public ResponseEntity<Cemetery> createCemetery(@RequestBody Cemetery cemetery) throws URISyntaxException {
        log.debug("REST request to save Cemetery : {}", cemetery);
        if (cemetery.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cemetery", "idexists", "A new cemetery cannot already have an ID")).body(null);
        }
        Cemetery result = cemeteryService.save(cemetery);
        return ResponseEntity.created(new URI("/api/cemeteries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cemetery", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cemeteries : Updates an existing cemetery.
     *
     * @param cemetery the cemetery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cemetery,
     * or with status 400 (Bad Request) if the cemetery is not valid,
     * or with status 500 (Internal Server Error) if the cemetery couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cemeteries")
    @Timed
    public ResponseEntity<Cemetery> updateCemetery(@RequestBody Cemetery cemetery) throws URISyntaxException {
        log.debug("REST request to update Cemetery : {}", cemetery);
        if (cemetery.getId() == null) {
            return createCemetery(cemetery);
        }
        Cemetery result = cemeteryService.save(cemetery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cemetery", cemetery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cemeteries : get all the cemeteries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cemeteries in body
     */
    @GetMapping("/cemeteries")
    @Timed
    public List<Cemetery> getAllCemeteries() {
        log.debug("REST request to get all Cemeteries");
        return cemeteryService.findAll();
    }

    /**
     * GET  /cemeteries/:id : get the "id" cemetery.
     *
     * @param id the id of the cemetery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cemetery, or with status 404 (Not Found)
     */
    @GetMapping("/cemeteries/{id}")
    @Timed
    public ResponseEntity<Cemetery> getCemetery(@PathVariable Long id) {
        log.debug("REST request to get Cemetery : {}", id);
        Cemetery cemetery = cemeteryService.findOne(id);
        return Optional.ofNullable(cemetery)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cemeteries/:id : delete the "id" cemetery.
     *
     * @param id the id of the cemetery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cemeteries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCemetery(@PathVariable Long id) {
        log.debug("REST request to delete Cemetery : {}", id);
        cemeteryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cemetery", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cemeteries?query=:query : search for the cemetery corresponding
     * to the query.
     *
     * @param query the query of the cemetery search 
     * @return the result of the search
     */
    @GetMapping("/_search/cemeteries")
    @Timed
    public List<Cemetery> searchCemeteries(@RequestParam String query) {
        log.debug("REST request to search Cemeteries for query {}", query);
        return cemeteryService.search(query);
    }


}
