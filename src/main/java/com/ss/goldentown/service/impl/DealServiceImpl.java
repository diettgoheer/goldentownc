package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.AgentService;
import com.ss.goldentown.service.BillingService;
import com.ss.goldentown.service.DealService;
import com.ss.goldentown.service.ProductService;
import com.ss.goldentown.domain.Deal;
import com.ss.goldentown.repository.DealRepository;
import com.ss.goldentown.repository.search.DealSearchRepository;
import com.ss.goldentown.service.dto.AgentDTO;
import com.ss.goldentown.service.dto.BillingDTO;
import com.ss.goldentown.service.dto.DealDTO;
import com.ss.goldentown.service.dto.ProductDTO;
import com.ss.goldentown.service.mapper.DealMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Deal.
 */
@Service
@Transactional
public class DealServiceImpl implements DealService{

    private final Logger log = LoggerFactory.getLogger(DealServiceImpl.class);
    
    @Inject
    private DealRepository dealRepository;

    @Inject
    private DealMapper dealMapper;

    @Inject
    private BillingService billService;
    
    @Inject
    private ProductService productService;
    
    @Inject
    private AgentService agentService;
    
    @Inject
    private DealSearchRepository dealSearchRepository;

    private double RAT = 0.618;
    
    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    public DealDTO save(DealDTO dealDTO) {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.dealDTOToDeal(dealDTO);
        deal = dealRepository.save(deal);
        DealDTO result = dealMapper.dealToDealDTO(deal);
        dealSearchRepository.save(deal);
        
        //save billings
        AgentDTO agentDTO = new AgentDTO();
        ProductDTO productDTO = productService.findOne(dealDTO.getProductId()) ;
        //判断是否存在代理商，如果存在
        if(dealDTO.getAgentId()!=null){
        	agentDTO=agentService.findOne(dealDTO.getAgentId());
        	//向代理商分成
        	long srcId = productDTO.getUserId();
    		long userId = agentDTO.getUserId();
        	for(int i = 1;i<6;i++){
        		BillingDTO billingDTO = new BillingDTO();
        		
        		double price = 0;
        		billingDTO.setDealId(dealDTO.getId());
        		billingDTO.setProductId(dealDTO.getProductId());       		
        		billingDTO.setUserId(userId);
        		billingDTO.setSrcId(srcId);
        		price = dealDTO.getPrice()*Math.exp((i*2+1)*Math.log(RAT));       		
        		billingDTO.setPrice(price);
        		log.debug("Request to save bill : {}", billingDTO);
        		billService.save(billingDTO);
        		if(agentDTO.getFatherId() == null)
        			break;
        		else{
        			agentDTO = agentService.findOne(agentDTO.getFatherId());
        			srcId = userId;
        			userId = agentDTO.getUserId();
        		}
        	}
        	
        }
        BillingDTO hostBillingDTO = new BillingDTO();
        hostBillingDTO.setDealId(dealDTO.getId());
        hostBillingDTO.setProductId(productDTO.getId());
        hostBillingDTO.setSrcId(dealDTO.getUserId());
        hostBillingDTO.setUserId(productDTO.getUserId());
        hostBillingDTO.setPrice(dealDTO.getPrice());
        log.debug("Request to save bill : {}", hostBillingDTO);
        billService.save(hostBillingDTO);
        return result;
    }

    /**
     *  Get all the deals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DealDTO> findAll() {
        log.debug("Request to get all Deals");
        List<DealDTO> result = dealRepository.findAll().stream()
        		.map(dealMapper::dealToDealDTO)
        		.collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
    
    @Transactional(readOnly = true) 
    public List<DealDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Deals");
        List<DealDTO> result = dealRepository.findByUserIsCurrentUser().stream()
        		.map(dealMapper::dealToDealDTO)
        		.collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one deal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DealDTO findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        Deal deal = dealRepository.findOne(id);
        DealDTO dealDTO = dealMapper.dealToDealDTO(deal);
        return dealDTO;
    }

    /**
     *  Delete the  deal by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Deal : {}", id);
        dealRepository.delete(id);
        dealSearchRepository.delete(id);
    }

    /**
     * Search for the deal corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DealDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Deals for query {}", query);
        Page<Deal> result = dealSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(deal -> dealMapper.dealToDealDTO(deal));
    }
}
