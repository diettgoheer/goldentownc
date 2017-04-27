package com.ss.goldentown.repository;

import com.ss.goldentown.domain.World;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the World entity.
 */
@SuppressWarnings("unused")
public interface WorldRepository extends JpaRepository<World,Long> {

}
