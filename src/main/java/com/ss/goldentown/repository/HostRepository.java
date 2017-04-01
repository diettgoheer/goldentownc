package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Host;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Host entity.
 */
@SuppressWarnings("unused")
public interface HostRepository extends JpaRepository<Host,Long> {

    @Query("select host from Host host where host.person.login = ?#{principal.username}")
    List<Host> findByPersonIsCurrentUser();

}
