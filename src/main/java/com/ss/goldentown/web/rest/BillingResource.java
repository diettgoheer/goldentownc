package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.service.BillingService;
import com.ss.goldentown.web.rest.util.HeaderUtil;
import com.ss.goldentown.service.dto.BillingDTO;

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
 * REST controller for managing Billing.
 */
@RestController
@RequestMapping("/api")
public class BillingResource {

    private final Logger log = LoggerFactory.getLogger(BillingResource.class);
        
    @Inject
    private BillingService billingService;

    /**
     * POST  /billings : Create a new billing.
     *
     * @param billingDTO the billingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingDTO, or with status 400 (Bad Request) if the billing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billings")
    @Timed
    public ResponseEntity<BillingDTO> createBilling(@RequestBody BillingDTO billingDTO) throws URISyntaxException {
        log.debug("REST request to save Billing : {}", billingDTO);
        if (billingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("billing", "idexists", "A new billing cannot already have an ID")).body(null);
        }
        BillingDTO result = billingService.save(billingDTO);
        return ResponseEntity.created(new URI("/api/billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("billing", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billings : Updates an existing billing.
     *
     * @param billingDTO the billingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingDTO,
     * or with status 400 (Bad Request) if the billingDTO is not valid,
     * or with status 500 (Internal Server Error) if the billingDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billings")
    @Timed
    public ResponseEntity<BillingDTO> updateBilling(@RequestBody BillingDTO billingDTO) throws URISyntaxException {
        log.debug("REST request to update Billing : {}", billingDTO);
        if (billingDTO.getId() == null) {
            return createBilling(billingDTO);
        }
        BillingDTO result = billingService.save(billingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("billing", billingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billings : get all the billings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billings in body
     */
    @GetMapping("/billings")
    @Timed
    public List<BillingDTO> getAllBillings() {
        log.debug("REST request to get all Billings");
        return billingService.findByUserIsCurrentUser();
    }

    /**
     * GET  /billings/:id : get the "id" billing.
     *
     * @param id the id of the billingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/billings/{id}")
    @Timed
    public ResponseEntity<BillingDTO> getBilling(@PathVariable Long id) {
        log.debug("REST request to get Billing : {}", id);
        BillingDTO billingDTO = billingService.findOne(id);
        return Optional.ofNullable(billingDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /billings/:id : delete the "id" billing.
     *
     * @param id the id of the billingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        log.debug("REST request to delete Billing : {}", id);
        billingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("billing", id.toString())).build();
    }

    /**
     * SEARCH  /_search/billings?query=:query : search for the billing corresponding
     * to the query.
     *
     * @param query the query of the billing search 
     * @return the result of the search
     */
    @GetMapping("/_search/billings")
    @Timed
    public List<BillingDTO> searchBillings(@RequestParam String query) {
        log.debug("REST request to search Billings for query {}", query);
        return billingService.search(query);
    }


}
