package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.service.BoardService;
import com.ss.goldentown.web.rest.util.HeaderUtil;
import com.ss.goldentown.web.rest.util.PaginationUtil;
import com.ss.goldentown.service.dto.BoardDTO;

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
 * REST controller for managing Board.
 */
@RestController
@RequestMapping("/api")
public class BoardResource {

    private final Logger log = LoggerFactory.getLogger(BoardResource.class);
        
    @Inject
    private BoardService boardService;

    /**
     * POST  /boards : Create a new board.
     *
     * @param boardDTO the boardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boardDTO, or with status 400 (Bad Request) if the board has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/boards")
    @Timed
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO) throws URISyntaxException {
        log.debug("REST request to save Board : {}", boardDTO);
        if (boardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("board", "idexists", "A new board cannot already have an ID")).body(null);
        }
        BoardDTO result = boardService.save(boardDTO);
        return ResponseEntity.created(new URI("/api/boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("board", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /boards : Updates an existing board.
     *
     * @param boardDTO the boardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boardDTO,
     * or with status 400 (Bad Request) if the boardDTO is not valid,
     * or with status 500 (Internal Server Error) if the boardDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/boards")
    @Timed
    public ResponseEntity<BoardDTO> updateBoard(@RequestBody BoardDTO boardDTO) throws URISyntaxException {
        log.debug("REST request to update Board : {}", boardDTO);
        if (boardDTO.getId() == null) {
            return createBoard(boardDTO);
        }
        BoardDTO result = boardService.save(boardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("board", boardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /boards : get all the boards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of boards in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/boards")
    @Timed
    public ResponseEntity<List<BoardDTO>> getAllBoards(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Boards");
        Page<BoardDTO> page = boardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/boards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /boards/:id : get the "id" board.
     *
     * @param id the id of the boardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/boards/{id}")
    @Timed
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long id) {
        log.debug("REST request to get Board : {}", id);
        BoardDTO boardDTO = boardService.findOne(id);
        return Optional.ofNullable(boardDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /boards/:id : delete the "id" board.
     *
     * @param id the id of the boardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/boards/{id}")
    @Timed
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        log.debug("REST request to delete Board : {}", id);
        boardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("board", id.toString())).build();
    }

    /**
     * SEARCH  /_search/boards?query=:query : search for the board corresponding
     * to the query.
     *
     * @param query the query of the board search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/boards")
    @Timed
    public ResponseEntity<List<BoardDTO>> searchBoards(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Boards for query {}", query);
        Page<BoardDTO> page = boardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/boards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
