package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.HostService;
import com.ss.goldentown.domain.Host;
import com.ss.goldentown.repository.HostRepository;
import com.ss.goldentown.repository.search.HostSearchRepository;
import com.ss.goldentown.service.dto.HostDTO;
import com.ss.goldentown.service.mapper.HostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Host.
 */
@Service
@Transactional
public class HostServiceImpl implements HostService{

    private final Logger log = LoggerFactory.getLogger(HostServiceImpl.class);
    
    @Inject
    private HostRepository hostRepository;

    @Inject
    private HostMapper hostMapper;

    @Inject
    private HostSearchRepository hostSearchRepository;

    /**
     * Save a host.
     *
     * @param hostDTO the entity to save
     * @return the persisted entity
     */
    public HostDTO save(HostDTO hostDTO) {
        log.debug("Request to save Host : {}", hostDTO);
        Host host = hostMapper.hostDTOToHost(hostDTO);
        host = hostRepository.save(host);
        HostDTO result = hostMapper.hostToHostDTO(host);
        hostSearchRepository.save(host);
        return result;
    }

    /**
     *  Get all the hosts.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<HostDTO> findAll() {
        log.debug("Request to get all Hosts");
        List<HostDTO> result = hostRepository.findAll().stream()
            .map(hostMapper::hostToHostDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one host by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HostDTO findOne(Long id) {
        log.debug("Request to get Host : {}", id);
        Host host = hostRepository.findOne(id);
        HostDTO hostDTO = hostMapper.hostToHostDTO(host);
        return hostDTO;
    }

    /**
     *  Delete the  host by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Host : {}", id);
        hostRepository.delete(id);
        hostSearchRepository.delete(id);
    }

    /**
     * Search for the host corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HostDTO> search(String query) {
        log.debug("Request to search Hosts for query {}", query);
        return StreamSupport
            .stream(hostSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(hostMapper::hostToHostDTO)
            .collect(Collectors.toList());
    }
}
