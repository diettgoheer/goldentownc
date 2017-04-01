package com.ss.goldentown.service.mapper;

import com.ss.goldentown.domain.*;
import com.ss.goldentown.service.dto.BoardDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Board and its DTO BoardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BoardMapper {

    BoardDTO boardToBoardDTO(Board board);

    List<BoardDTO> boardsToBoardDTOs(List<Board> boards);

    Board boardDTOToBoard(BoardDTO boardDTO);

    List<Board> boardDTOsToBoards(List<BoardDTO> boardDTOs);
}
