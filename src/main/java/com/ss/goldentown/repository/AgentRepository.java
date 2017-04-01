package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Agent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Agent entity.
 */
@SuppressWarnings("unused")
public interface AgentRepository extends JpaRepository<Agent,Long> {

    @Query("select agent from Agent agent where agent.user.login = ?#{principal.username}")
    List<Agent> findByUserIsCurrentUser();

}
