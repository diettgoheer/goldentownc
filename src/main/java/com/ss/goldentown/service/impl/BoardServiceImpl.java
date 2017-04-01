package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.BoardService;
import com.ss.goldentown.domain.Board;
import com.ss.goldentown.repository.BoardRepository;
import com.ss.goldentown.repository.search.BoardSearchRepository;
import com.ss.goldentown.service.dto.BoardDTO;
import com.ss.goldentown.service.mapper.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Board.
 */
@Service
@Transactional
public class BoardServiceImpl implements BoardService{

    private final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
    
    @Inject
    private BoardRepository boardRepository;

    @Inject
    private BoardMapper boardMapper;

    @Inject
    private BoardSearchRepository boardSearchRepository;

    /**
     * Save a board.
     *
     * @param boardDTO the entity to save
     * @return the persisted entity
     */
    public BoardDTO save(BoardDTO boardDTO) {
        log.debug("Request to save Board : {}", boardDTO);
        Board board = boardMapper.boardDTOToBoard(boardDTO);
        board = boardRepository.save(board);
        BoardDTO result = boardMapper.boardToBoardDTO(board);
        boardSearchRepository.save(board);
        return result;
    }

    /**
     *  Get all the boards.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<BoardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Boards");
        Page<Board> result = boardRepository.findAll(pageable);
        return result.map(board -> boardMapper.boardToBoardDTO(board));
    }

    /**
     *  Get one board by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public BoardDTO findOne(Long id) {
        log.debug("Request to get Board : {}", id);
        Board board = boardRepository.findOne(id);
        BoardDTO boardDTO = boardMapper.boardToBoardDTO(board);
        return boardDTO;
    }

    /**
     *  Delete the  board by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Board : {}", id);
        boardRepository.delete(id);
        boardSearchRepository.delete(id);
    }

    /**
     * Search for the board corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BoardDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Boards for query {}", query);
        Page<Board> result = boardSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(board -> boardMapper.boardToBoardDTO(board));
    }
}
