package com.ss.goldentown.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ss.goldentown.domain.World;
import com.ss.goldentown.service.WorldService;
import com.ss.goldentown.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing World.
 */
@RestController
@RequestMapping("/api")
public class WorldResource {

    private final Logger log = LoggerFactory.getLogger(WorldResource.class);
        
    @Inject
    private WorldService worldService;

    /**
     * POST  /worlds : Create a new world.
     *
     * @param world the world to create
     * @return the ResponseEntity with status 201 (Created) and with body the new world, or with status 400 (Bad Request) if the world has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/worlds")
    @Timed
    public ResponseEntity<World> createWorld(@RequestBody World world) throws URISyntaxException {
        log.debug("REST request to save World : {}", world);
        if (world.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("world", "idexists", "A new world cannot already have an ID")).body(null);
        }
        World result = worldService.save(world);
        return ResponseEntity.created(new URI("/api/worlds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("world", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /worlds : Updates an existing world.
     *
     * @param world the world to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated world,
     * or with status 400 (Bad Request) if the world is not valid,
     * or with status 500 (Internal Server Error) if the world couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/worlds")
    @Timed
    public ResponseEntity<World> updateWorld(@RequestBody World world) throws URISyntaxException {
        log.debug("REST request to update World : {}", world);
        if (world.getId() == null) {
            return createWorld(world);
        }
        World result = worldService.save(world);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("world", world.getId().toString()))
            .body(result);
    }

    /**
     * GET  /worlds : get all the worlds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of worlds in body
     */
    @GetMapping("/worlds")
    @Timed
    public List<World> getAllWorlds() {
        log.debug("REST request to get all Worlds");
        return worldService.findAll();
    }

    /**
     * GET  /worlds/:id : get the "id" world.
     *
     * @param id the id of the world to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the world, or with status 404 (Not Found)
     */
    @GetMapping("/worlds/{id}")
    @Timed
    public ResponseEntity<World> getWorld(@PathVariable Long id) {
        log.debug("REST request to get World : {}", id);
        World world = worldService.findOne(id);
        return Optional.ofNullable(world)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /worlds/:id : delete the "id" world.
     *
     * @param id the id of the world to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/worlds/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorld(@PathVariable Long id) {
        log.debug("REST request to delete World : {}", id);
        worldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("world", id.toString())).build();
    }

    /**
     * SEARCH  /_search/worlds?query=:query : search for the world corresponding
     * to the query.
     *
     * @param query the query of the world search 
     * @return the result of the search
     */
    @GetMapping("/_search/worlds")
    @Timed
    public List<World> searchWorlds(@RequestParam String query) {
        log.debug("REST request to search Worlds for query {}", query);
        return worldService.search(query);
    }


}
