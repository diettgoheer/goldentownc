package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Cemetery;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cemetery entity.
 */
@SuppressWarnings("unused")
public interface CemeteryRepository extends JpaRepository<Cemetery,Long> {

}
