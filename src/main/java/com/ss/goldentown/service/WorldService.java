package com.ss.goldentown.service;

import com.ss.goldentown.domain.World;
import java.util.List;

/**
 * Service Interface for managing World.
 */
public interface WorldService {

    /**
     * Save a world.
     *
     * @param world the entity to save
     * @return the persisted entity
     */
    World save(World world);

    /**
     *  Get all the worlds.
     *  
     *  @return the list of entities
     */
    List<World> findAll();

    /**
     *  Get the "id" world.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    World findOne(Long id);

    /**
     *  Delete the "id" world.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the world corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<World> search(String query);
}
