package com.ss.goldentown.service.impl;

import com.ss.goldentown.service.ProductService;
import com.ss.goldentown.domain.Product;
import com.ss.goldentown.repository.ProductRepository;
import com.ss.goldentown.repository.search.ProductSearchRepository;
import com.ss.goldentown.service.dto.ProductDTO;
import com.ss.goldentown.service.mapper.ProductMapper;
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
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductSearchRepository productSearchRepository;

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.productDTOToProduct(productDTO);
        product = productRepository.save(product);
        ProductDTO result = productMapper.productToProductDTO(product);
        productSearchRepository.save(product);
        return result;
    }

    /**
     *  Get all the products.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProductDTO> findAll() {
        log.debug("Request to get all Products");
        List<ProductDTO> result = productRepository.findAll().stream()
            .map(productMapper::productToProductDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get all the products is current user.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProductDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Products");
        List<ProductDTO> result = productRepository.findByUserIsCurrentUser().stream()
            .map(productMapper::productToProductDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }    
    
    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductDTO findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        return productDTO;
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
        productSearchRepository.delete(id);
    }

    /**
     * Search for the product corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> search(String query) {
        log.debug("Request to search Products for query {}", query);
        return StreamSupport
            .stream(productSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productMapper::productToProductDTO)
            .collect(Collectors.toList());
    }
}
