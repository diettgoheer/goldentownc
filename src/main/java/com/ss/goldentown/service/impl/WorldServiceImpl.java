package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.WorldService;
import com.ss.goldentown.domain.World;
import com.ss.goldentown.repository.WorldRepository;
import com.ss.goldentown.repository.search.WorldSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing World.
 */
@Service
@Transactional
public class WorldServiceImpl implements WorldService{

    private final Logger log = LoggerFactory.getLogger(WorldServiceImpl.class);
    
    @Inject
    private WorldRepository worldRepository;

    @Inject
    private WorldSearchRepository worldSearchRepository;

    /**
     * Save a world.
     *
     * @param world the entity to save
     * @return the persisted entity
     */
    public World save(World world) {
        log.debug("Request to save World : {}", world);
        World result = worldRepository.save(world);
        worldSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the worlds.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<World> findAll() {
        log.debug("Request to get all Worlds");
        List<World> result = worldRepository.findAll();

        return result;
    }

    /**
     *  Get one world by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public World findOne(Long id) {
        log.debug("Request to get World : {}", id);
        World world = worldRepository.findOne(id);
        return world;
    }

    /**
     *  Delete the  world by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete World : {}", id);
        worldRepository.delete(id);
        worldSearchRepository.delete(id);
    }

    /**
     * Search for the world corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<World> search(String query) {
        log.debug("Request to search Worlds for query {}", query);
        return StreamSupport
            .stream(worldSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
