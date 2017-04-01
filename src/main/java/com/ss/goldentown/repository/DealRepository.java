package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Deal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Deal entity.
 */
@SuppressWarnings("unused")
public interface DealRepository extends JpaRepository<Deal,Long> {

    @Query("select deal from Deal deal where deal.user.login = ?#{principal.username}")
    List<Deal> findByUserIsCurrentUser();

}
