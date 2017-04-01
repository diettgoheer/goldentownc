package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Billing;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Billing entity.
 */
@SuppressWarnings("unused")
public interface BillingRepository extends JpaRepository<Billing,Long> {

    @Query("select billing from Billing billing where billing.user.login = ?#{principal.username}")
    List<Billing> findByUserIsCurrentUser();

    @Query("select billing from Billing billing where billing.src.login = ?#{principal.username}")
    List<Billing> findBySrcIsCurrentUser();

}
