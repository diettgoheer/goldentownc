package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.service.DealService;
import com.ss.goldentown.web.rest.util.HeaderUtil;
import com.ss.goldentown.web.rest.util.PaginationUtil;
import com.ss.goldentown.service.dto.DealDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Deal.
 */
@RestController
@RequestMapping("/api")
public class DealResource {

    private final Logger log = LoggerFactory.getLogger(DealResource.class);
        
    @Inject
    private DealService dealService;

    /**
     * POST  /deals : Create a new deal.
     *
     * @param dealDTO the dealDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealDTO, or with status 400 (Bad Request) if the deal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deals")
    @Timed
    public ResponseEntity<DealDTO> createDeal(@RequestBody DealDTO dealDTO) throws URISyntaxException {
        log.debug("REST request to save Deal : {}", dealDTO);
        if (dealDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("deal", "idexists", "A new deal cannot already have an ID")).body(null);
        }
        DealDTO result = dealService.save(dealDTO);
        return ResponseEntity.created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deals : Updates an existing deal.
     *
     * @param dealDTO the dealDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dealDTO,
     * or with status 400 (Bad Request) if the dealDTO is not valid,
     * or with status 500 (Internal Server Error) if the dealDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deals")
    @Timed
    public ResponseEntity<DealDTO> updateDeal(@RequestBody DealDTO dealDTO) throws URISyntaxException {
        log.debug("REST request to update Deal : {}", dealDTO);
        if (dealDTO.getId() == null) {
            return createDeal(dealDTO);
        }
        DealDTO result = dealService.save(dealDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deal", dealDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deals : get all the deals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deals in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/deals")
    @Timed
    public List<DealDTO> getAllDeals()
        throws URISyntaxException {
        log.debug("REST request to get a page of Deals");
        return dealService.findByUserIsCurrentUser();
    }

    /**
     * GET  /deals/:id : get the "id" deal.
     *
     * @param id the id of the dealDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dealDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deals/{id}")
    @Timed
    public ResponseEntity<DealDTO> getDeal(@PathVariable Long id) {
        log.debug("REST request to get Deal : {}", id);
        DealDTO dealDTO = dealService.findOne(id);
        return Optional.ofNullable(dealDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deals/:id : delete the "id" deal.
     *
     * @param id the id of the dealDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deals/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        log.debug("REST request to delete Deal : {}", id);
        dealService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deal", id.toString())).build();
    }

    /**
     * SEARCH  /_search/deals?query=:query : search for the deal corresponding
     * to the query.
     *
     * @param query the query of the deal search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/deals")
    @Timed
    public ResponseEntity<List<DealDTO>> searchDeals(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Deals for query {}", query);
        Page<DealDTO> page = dealService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/deals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
