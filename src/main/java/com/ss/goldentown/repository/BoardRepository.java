package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Board;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Board entity.
 */
@SuppressWarnings("unused")
public interface BoardRepository extends JpaRepository<Board,Long> {

}
