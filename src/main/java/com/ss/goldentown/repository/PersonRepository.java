package com.ss.goldentown.repository;

import com.ss.goldentown.domain.Person;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
public interface PersonRepository extends JpaRepository<Person,Long> {
	List<Person> findAllByIsDead(boolean isDead);
}
